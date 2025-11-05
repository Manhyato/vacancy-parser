package com.example.vacancy_parser.task;

public class WorkerThread extends Thread {
    public WorkerThread() {
        super("WorkerThread");
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 3; i++) {
                System.out.println(getName() + " working iteration " + i);
                Thread.sleep(500); // TIMED_WAITING
            }
        } catch (InterruptedException e) {
            interrupt();
        }
    }
}

