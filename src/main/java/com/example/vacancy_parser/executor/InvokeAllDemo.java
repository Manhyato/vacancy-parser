package com.example.vacancy_parser.executor;

import java.util.*;
import java.util.concurrent.*;

public class InvokeAllDemo {

    public static void runDemo() {
        System.out.println("=== Демонстрация invokeAll() ===");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            int id = i;
            tasks.add(() -> {
                int delay = new Random().nextInt(3000) + 1000;
                Thread.sleep(delay);
                return "Задача " + id + " завершена за " + delay + " мс";
            });
        }

        try {
            List<Future<String>> results = executor.invokeAll(tasks);
            for (Future<String> result : results) {
                System.out.println(result.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        System.out.println();
    }
}

