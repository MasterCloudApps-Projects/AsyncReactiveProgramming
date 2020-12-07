package es.codeurjc.arpj;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class StructuredConcurrency {

    public static void main(String[] args) {

        ThreadFactory factory = Thread.builder().virtual()
                .name("my-vt-factory-1").factory();

        try (final var executorService = Executors.newThreadExecutor(factory)) {

            for (int i = 0; i < 500; i++) {

                final var index = i;

                if (index % 2 == 0) {
                    executorService.submit(() -> taskTwo(index));
                } else {
                    executorService.submit(() -> taskOne(index));
                }
            }

            System.out.println("\nAll tasks have been submitted!!!\n");
        }

        System.out.println("\nEverything has finished!!!\n");
    }

    private static void taskOne(final int taskNumber) {

        ThreadFactory factory = Thread.builder().virtual()
                .name("my-factory-task-one").factory();

        try (final var executorService = Executors.newThreadExecutor(factory)) {

            executorService.submit(() -> print("t-one", taskNumber));
        }
    }

    private static void taskTwo(final int taskNumber) {

        ThreadFactory factory = Thread.builder().virtual()
                .name("my-factory-task-two").factory();

        try (final var executorService = Executors.newThreadExecutor(factory)) {

            executorService.submit(() -> print("t-two", taskNumber));
        }
    }

    private static void print(final String task, final int taskNumber) {

        if (taskNumber % 2 == 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Normal task  [" + task + "] - " + taskNumber + "... !!! @ "
                + Thread.currentThread().getName());
    }
}
