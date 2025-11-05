package com.example.vacancy_parser.concurrency;

/**
 * Демонстрация безопасной остановки потока через volatile.
 */
public class VolatileStopExample implements Runnable {

    private volatile boolean running = true;

    @Override
    public void run() {
        System.out.println("Рабочий поток запущен...");
        while (running) {
            // имитация работы
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Рабочий поток остановлен корректно!");
    }

    public void stop() {
        running = false;
    }
}

