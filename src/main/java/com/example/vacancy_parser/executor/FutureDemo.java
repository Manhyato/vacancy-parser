package com.example.vacancy_parser.executor;

import java.util.concurrent.*;

public class FutureDemo {

    public static void runDemo() {
        System.out.println("=== Демонстрация Future и отмены задачи ===");

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Выполняется задача... шаг " + (i + 1));
                    Thread.sleep(1000);
                }
                System.out.println("Задача завершена успешно");
            } catch (InterruptedException e) {
                System.out.println("Задача прервана!");
                Thread.currentThread().interrupt();
            }
        });

        try {
            Thread.sleep(2500);
            System.out.println("Попытка отменить задачу...");
            boolean cancelled = future.cancel(true);
            System.out.println("Задача отменена: " + cancelled);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdownNow();
        }
        System.out.println();
    }
}

