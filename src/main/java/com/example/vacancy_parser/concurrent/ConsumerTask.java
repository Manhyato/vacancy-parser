package com.example.vacancy_parser.concurrent;

import com.example.vacancy_parser.model.Item;

public class ConsumerTask implements Runnable {
    private final DataCollector collector;
    private final int id;
    private volatile boolean running = true;

    public ConsumerTask(DataCollector collector, int id) {
        this.collector = collector;
        this.id = id;
    }

    public void stop() { running = false; }

    @Override
    public void run() {
        while (running || collector.snapshot().get("queueSize") instanceof Integer && ((Integer)collector.snapshot().get("queueSize")) > 0) {
            try {
                Item item = collector.takeItemOrWait(); // ждёт, если пусто
                // обработка
                // (проверяем на дубликат, и помечаем как обработанное)
                if (!collector.isAlreadyProcessed(item.getKey())) {
                    // имитация обработки
                    try { Thread.sleep(3); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    collector.markProcessed(item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
