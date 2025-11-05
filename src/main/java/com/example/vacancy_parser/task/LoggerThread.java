package com.example.vacancy_parser.task;

public class LoggerThread extends Thread{
    
    private final int iterations;

    public LoggerThread(int iterations) {
        super("LoggerThread");
        this.iterations = iterations;
        setDaemon(false);
    }

    @Override
    public void run() {
        for (int i = 1; i <= iterations; i++) {
            System.out.println("[" + getName() + "] log #" + i);
            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
