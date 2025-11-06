package com.example.vacancy_parser.executor;

import java.util.*;
import java.util.concurrent.*;

public class PeriodicDataAggregator {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final Random random = new Random();

    public void startAggregation() {
        Runnable aggregationTask = () -> {
            List<String> entities = List.of("USD", "EUR", "BTC", "ETH", "JPY");
            System.out.println("\n=== Агрегация данных (" + System.currentTimeMillis() + ") ===");

            List<Future<String>> results = new ArrayList<>();
            for (String entity : entities) {
                results.add(executor.submit(() -> fetchData(entity)));
            }

            for (Future<String> f : results) {
                try {
                    System.out.println("Результат: " + f.get());
                } catch (Exception e) {
                    System.err.println("Ошибка при обработке данных: " + e.getMessage());
                }
            }
        };

        scheduler.scheduleAtFixedRate(aggregationTask, 0, 5, TimeUnit.SECONDS);
    }

    private String fetchData(String entity) throws Exception {
        int delay = random.nextInt(3000) + 1000;
        Thread.sleep(delay);
        if (random.nextInt(10) < 2) { // 20% шанс ошибки
            throw new Exception("Ошибка при получении данных " + entity);
        }
        return entity + " обновлено за " + delay + " мс";
    }

    public void shutdown() {
        scheduler.shutdown();
        executor.shutdown();
    }
}
