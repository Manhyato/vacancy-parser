package com.example.vacancy_parser.concurrent;

import com.example.vacancy_parser.model.Item;

public class ProducerTask implements Runnable {
    private final DataCollector collector;
    private final int itemsToProduce;
    private final int id;

    public ProducerTask(DataCollector collector, int id, int itemsToProduce) {
        this.collector = collector;
        this.id = id;
        this.itemsToProduce = itemsToProduce;
    }

    @Override
    public void run() {
        for (int i = 0; i < itemsToProduce; i++) {
            String key = "P" + id + "-item-" + i;
            Item it = new Item(key, "payload-" + i);
            collector.collectItem(it);
            // имитация работы
            try { Thread.sleep(2); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
        }
    }
}