package com.example.vacancy_parser;

import com.example.vacancy_parser.buffer.BoundedBuffer;
import com.example.vacancy_parser.buffer.Producer;
import com.example.vacancy_parser.buffer.Consumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Класс для демонстрации работы потокобезопасного буфера с использованием ReentrantLock и Condition.
 */
@Component
public class DemoRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Запуск демо BoundedBuffer (ReentrantLock + Condition) ===");
        runBufferDemo();
    }

    private void runBufferDemo() throws InterruptedException {
        final int bufferSize = 10;
        final int producerCount = 3;
        final int consumerCount = 3;
        final int itemsPerProducer = 50;

        BoundedBuffer<Integer> buffer = new BoundedBuffer<>(bufferSize);
        ExecutorService pool = Executors.newFixedThreadPool(producerCount + consumerCount);

        long t0 = System.currentTimeMillis();

        // Запускаем продюсеров
        for (int i = 0; i < producerCount; i++) {
            pool.submit(new Producer(buffer, i, itemsPerProducer));
        }

        // Запускаем консумеров
        for (int i = 0; i < consumerCount; i++) {
            pool.submit(new Consumer(buffer, i));
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        long t1 = System.currentTimeMillis();
        System.out.println("=== Демо завершено ===");
        System.out.println("Время выполнения: " + (t1 - t0) + " ms");
    }
}

