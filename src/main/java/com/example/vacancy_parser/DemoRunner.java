package com.example.vacancy_parser;

import com.example.vacancy_parser.concurrency.AtomicCache;
import com.example.vacancy_parser.concurrency.AtomicCounter;
import com.example.vacancy_parser.concurrency.VolatileStopExample;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class DemoRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Демонстрация volatile и atomic переменных ===");

        runVolatileDemo();
        runAtomicCounterDemo();
        runAtomicCacheDemo();
    }

    private void runVolatileDemo() throws InterruptedException {
        System.out.println("\n--- Volatile Stop Example ---");
        VolatileStopExample task = new VolatileStopExample();
        Thread t = new Thread(task);
        t.start();

        Thread.sleep(1000);
        System.out.println("Останавливаем поток...");
        task.stop();
        t.join();
    }

    private void runAtomicCounterDemo() throws InterruptedException {
        System.out.println("\n--- Atomic Counter Example ---");
        AtomicCounter counter = new AtomicCounter();

        int threads = 10;
        int incrementsPerThread = 100_000;

        ExecutorService pool = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            pool.submit(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("Ожидаемое значение: " + (threads * incrementsPerThread));
        System.out.println("Реальное значение:   " + counter.getValue());
    }

    private void runAtomicCacheDemo() throws InterruptedException {
        System.out.println("\n--- Atomic Cache Example ---");
        AtomicCache cache = new AtomicCache();

        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int id = i;
            pool.submit(() -> {
                String value = cache.getOrCreate("CreatedByThread-" + id);
                System.out.println("Поток " + id + " получил значение: " + value);
            });
        }

        pool.shutdown();
        pool.awaitTermination(2, TimeUnit.SECONDS);

        System.out.println("Финальное значение в кэше: " + cache.getValue());
    }
}


