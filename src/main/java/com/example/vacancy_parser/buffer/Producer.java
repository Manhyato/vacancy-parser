package com.example.vacancy_parser.buffer;

public class Producer implements Runnable {
    private final BoundedBuffer<Integer> buffer;
    private final int id;
    private final int itemsToProduce;

    public Producer(BoundedBuffer<Integer> buffer, int id, int itemsToProduce) {
        this.buffer = buffer;
        this.id = id;
        this.itemsToProduce = itemsToProduce;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < itemsToProduce; i++) {
                buffer.put(i);
                if (i % 100 == 0)
                    System.out.println("Producer " + id + " added item " + i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
