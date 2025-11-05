package com.example.vacancy_parser.buffer;

public class Consumer implements Runnable {
    private final BoundedBuffer<Integer> buffer;
    private final int id;

    public Consumer(BoundedBuffer<Integer> buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer item = buffer.take();
                if (item % 100 == 0)
                    System.out.println("Consumer " + id + " processed item " + item);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

