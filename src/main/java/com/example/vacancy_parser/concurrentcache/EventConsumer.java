package com.example.vacancy_parser.concurrentcache;

import java.util.concurrent.BlockingQueue;

public class EventConsumer implements Runnable {
    private final BlockingQueue<Event> queue;
    private final EventCache cache;

    public EventConsumer(BlockingQueue<Event> queue, EventCache cache) {
        this.queue = queue;
        this.cache = cache;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Event event = queue.take();
                cache.put(event.getType() + "-" + event.getTimestamp(), event);
                System.out.println("Потребитель обработал: " + event);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
