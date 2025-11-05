package com.example.vacancy_parser.util;

import com.example.vacancy_parser.task.BlockedThread;
import com.example.vacancy_parser.task.WaitingThread;
import com.example.vacancy_parser.task.WorkerThread;
import org.springframework.stereotype.Component;

@Component
public class ThreadStateDemo {

    public void runDemo() throws InterruptedException {
        Object lock = new Object();

        WorkerThread worker = new WorkerThread();
        WaitingThread waiting = new WaitingThread(lock);
        BlockedThread blocked1 = new BlockedThread(lock);
        BlockedThread blocked2 = new BlockedThread(lock); // этот станет BLOCKED

        System.out.println("Initial states:");
        printStates(worker, waiting, blocked1, blocked2);

        // Запускаем потоки
        worker.start();
        blocked1.start();
        Thread.sleep(100);
        blocked2.start();
        waiting.start();

        // Наблюдаем состояния в цикле
        for (int i = 0; i < 10; i++) {
            printStates(worker, waiting, blocked1, blocked2);
            Thread.sleep(300);
        }

        // Разбудим waiting поток
        synchronized (lock) {
            lock.notify();
        }

        worker.join();
        blocked1.join();
        blocked2.join();
        waiting.join();

        System.out.println("Final states:");
        printStates(worker, waiting, blocked1, blocked2);
    }

    private void printStates(Thread... threads) {
        for (Thread t : threads) {
            System.out.println("[" + t.getName() + "] state: " + t.getState());
        }
        System.out.println("----------------------------");
    }
}
