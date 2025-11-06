package com.example.vacancy_parser.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StarvationDemo {

    private final Lock lock = new ReentrantLock();

    public void startStarvation() throws InterruptedException {
        // "жадный" поток: постоянно захватывает lock и делает небольшую работу
        Runnable greedy = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                lock.lock();
                try {
                    // короткая работа
                    try { Thread.sleep(1); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                } finally {
                    lock.unlock();
                }
            }
        };

        // "голодающий" поток: пытается захватить lock, но из-за высокой конкуренции редко получает его
        Runnable hungry = () -> {
            int attempts = 0;
            while (attempts < 1000) {
                if (lock.tryLock()) {
                    try {
                        System.out.println("Hungry получил lock на попытке " + attempts);
                        break;
                    } finally {
                        lock.unlock();
                    }
                } else {
                    attempts++;
                    // без yield/таймаута hungry может долго ждать
                }
            }
            if (attempts >= 1000) System.out.println("Hungry: возможно starvation — не получил lock");
        };

        Thread g1 = new Thread(greedy, "Greedy-1");
        Thread g2 = new Thread(greedy, "Greedy-2");
        Thread hungryThread = new Thread(hungry, "Hungry");

        g1.setDaemon(true);
        g2.setDaemon(true);
        g1.start();
        g2.start();
        hungryThread.start();

        hungryThread.join();
    }
}

