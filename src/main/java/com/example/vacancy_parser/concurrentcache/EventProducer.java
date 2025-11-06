package com.example.vacancy_parser.concurrentcache;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class EventProducer implements Runnable {
    private final BlockingQueue<Event> queue;
    private final String producerName;

    public EventProducer(String name, BlockingQueue<Event> queue) {
        this.producerName = name;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                Event event = new Event("INFO", producerName + " - событие #" + i);
                queue.put(event);
                System.out.println(producerName + " сгенерировал: " + event);
                Thread.sleep(ThreadLocalRandom.current().nextInt(200, 600));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
