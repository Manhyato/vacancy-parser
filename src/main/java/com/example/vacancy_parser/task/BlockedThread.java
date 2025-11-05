package com.example.vacancy_parser.task;

public class BlockedThread extends Thread {
    private final Object lock;

    public BlockedThread(Object lock) {
        super("BlockedThread");
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            System.out.println(getName() + " entered synchronized block.");
            try {
                Thread.sleep(2000); // держим блок
            } catch (InterruptedException e) {
                interrupt();
            }
            System.out.println(getName() + " released lock.");
        }
    }
}