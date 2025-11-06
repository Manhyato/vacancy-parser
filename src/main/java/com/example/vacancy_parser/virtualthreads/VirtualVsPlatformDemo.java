package com.example.vacancy_parser.virtualthreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class VirtualVsPlatformDemo {

    private static final int TASK_COUNT = 10_000;

    public static void main(String[] args) throws Exception {
        System.out.println("=== Сравнение эмуляции виртуальных и обычных потоков ===");

        long virtualTime = runSimulatedVirtualThreads();
        long platformTime = runPlatformThreads();

        System.out.println("\n=== Результаты ===");
        System.out.println("Псевдо-виртуальные потоки: " + virtualTime + " мс");
        System.out.println("Платформенные потоки: " + platformTime + " мс");
    }

    // Эмуляция виртуальных потоков (через пул потоков)
    private static long runSimulatedVirtualThreads() throws InterruptedException {
        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(200); // имитация лёгких потоков

        for (int i = 0; i < TASK_COUNT; i++) {
            final int id = i;
            executor.submit(() -> simulateTask("SimVirtual-" + id));
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        long end = System.currentTimeMillis();
        System.out.println("Эмуляция виртуальных потоков завершилась за: " + (end - start) + " мс");
        return end - start;
    }

    // Обычные платформенные потоки
    private static long runPlatformThreads() throws InterruptedException {
        long start = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < TASK_COUNT; i++) {
            final int id = i;
            Thread t = new Thread(() -> simulateTask("Platform-" + id));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) t.join();

        long end = System.currentTimeMillis();
        System.out.println("Платформенные потоки завершили выполнение за: " + (end - start) + " мс");
        return end - start;
    }

    // Имитация задачи
    private static void simulateTask(String name) {
        try {
            if (name.endsWith("0")) {
                System.out.println(Thread.currentThread().getName() + " выполняет " + name);
            }
            Thread.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}



