package com.example.vacancy_parser.task;

import com.example.vacancy_parser.service.SimpleService;;

public class TaskRunnable implements Runnable {

    private final SimpleService service;
    private final int iterations;
    
    public TaskRunnable(SimpleService service, int iterations) {
        this.service = service;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("CounterWorker");
        for (int i = 0; i < iterations; i++) {
            int value = service.incrementAndGet();
            System.out.println("[" + Thread.currentThread().getName() + "] count=" + value);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
