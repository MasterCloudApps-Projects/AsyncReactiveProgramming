package es.codeurjc.arpj;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CancellationDeadline {

    public static void main(String[] args) {

        final AtomicInteger success = new AtomicInteger();
        final AtomicInteger fail    = new AtomicInteger();

        try (final var executorService = Executors.newVirtualThreadExecutor()
                .withDeadline(Instant.now().plusSeconds(1))) {

            for (int i = 0; i < 500; i++) {

                final var index = i;
                executorService.submit(() -> {
                    try {
                        print(index);
                        success.incrementAndGet();
                    } catch (final InterruptedException e) {
                        System.out.println("Interrupted exception !!!");
                        fail.incrementAndGet();
                    }
                });
            }

            System.out.println("\nAll tasks have been submitted!!!\n");
        }

        System.out.println("\nEverything has finished!!!\n");
        System.out.println("Success: " + success.get());
        System.out.println("Fail   : " + fail.get());
    }

    private static void print(final int taskNumber)
            throws InterruptedException {

        if (taskNumber % 2 == 0) {
            Thread.sleep(965);
        }

        System.out.println("Normal task  [my-task] - " + taskNumber + "... !!! @ "
                + Thread.currentThread().getName());
    }
}
