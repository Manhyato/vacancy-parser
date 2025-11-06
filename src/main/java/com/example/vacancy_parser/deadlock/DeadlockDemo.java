package com.example.vacancy_parser.deadlock;

/**
 * Демонстрация классического deadlock.
 */
public class DeadlockDemo {

    private final Object resourceA = new Object();
    private final Object resourceB = new Object();

    public void startDeadlock() {
        Thread t1 = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println("T1: захватил resourceA, ждёт немного...");
                sleep(200);
                System.out.println("T1: пытается захватить resourceB...");
                synchronized (resourceB) {
                    System.out.println("T1: захватил resourceB (этого не произойдёт при deadlock)");
                }
            }
        }, "Deadlock-Thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println("T2: захватил resourceB, ждёт немного...");
                sleep(200);
                System.out.println("T2: пытается захватить resourceA...");
                synchronized (resourceA) {
                    System.out.println("T2: захватил resourceA (этого не произойдёт при deadlock)");
                }
            }
        }, "Deadlock-Thread-2");

        t1.start();
        t2.start();
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
    }
}

