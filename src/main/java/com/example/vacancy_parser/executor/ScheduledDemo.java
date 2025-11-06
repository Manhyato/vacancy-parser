package com.example.vacancy_parser.executor;

import java.util.concurrent.*;

public class ScheduledDemo {

    public static void runDemo() {
        System.out.println("=== Демонстрация ScheduledExecutorService ===");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Проверка статуса системы: " + System.currentTimeMillis());

        // Запуск задачи каждые 2 секунды
        scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);

        try {
            Thread.sleep(7000); // пусть поработает немного
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            scheduler.shutdown();
            System.out.println("Планировщик остановлен.");
        }
        System.out.println();
    }
}
