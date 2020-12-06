package es.codeurjc.arpj;

import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreeMillionTasks {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static void main(String[] args) {

        ThreadFactory factory = Thread.builder().virtual()
                .name("my-vt-factory").factory();

        final ExecutorService executorService =
                Executors.newThreadExecutor(factory);

        for (int i = 0; i < 3_000_000; i++) {
            int index = i;
            executorService.submit(() -> {
                try {
                    task(index);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.close();
    }


    private static void task(final int taskNumber)
            throws InterruptedException {

        System.out.println("Sleeping task " + taskNumber + "... !!! @ "
                + Thread.currentThread().getName());

        Thread.sleep(RANDOM.nextInt(10) * 500L);
    }
}
