package com.example.vacancy_parser.concurrentcache;

import java.util.concurrent.*;

public class EventSystemDemo {
    public static void runDemo() throws InterruptedException {
        BlockingQueue<Event> queue = new LinkedBlockingQueue<>();
        EventCache cache = new EventCache();

        ExecutorService executor = Executors.newCachedThreadPool();

        executor.submit(new EventConsumer(queue, cache));

        for (int i = 1; i <= 3; i++) {
            executor.submit(new EventProducer("Производитель-" + i, queue));
        }

        Thread.sleep(5000);
        executor.shutdownNow();

        System.out.println("\n=== Кэшированные события ===");
        cache.printAll();
        System.out.println("Всего событий в кэше: " + cache.size());
    }
}

