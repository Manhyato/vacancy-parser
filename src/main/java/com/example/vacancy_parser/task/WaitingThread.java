package com.example.vacancy_parser.task;

public class WaitingThread extends Thread {
    private final Object lock;

    public WaitingThread(Object lock) {
        super("WaitingThread");
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                System.out.println(getName() + " waiting...");
                lock.wait(); // WAITING
                System.out.println(getName() + " resumed and finished.");
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
}