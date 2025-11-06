package com.example.vacancy_parser.deadlock;

public class LivelockDemo {

    static class PoliteWorker {
        private String name;
        private volatile boolean busy;

        public PoliteWorker(String name) {
            this.name = name;
            this.busy = true;
        }

        public void workWith(PoliteWorker other) {
            int attempts = 0;
            while (busy && other.busy && attempts < 10) {
                System.out.println(name + " уступает место " + other.name);
                // уступает место — делает паузу, ожидая, что другой изменит состояние
                sleep(100);
                attempts++;
                // условие, при котором оба продолжают уступать → livelock
            }
            if (!other.busy) {
                System.out.println(name + " выполняет работу.");
                busy = false;
            } else {
                System.out.println(name + " не смог выполнить (livelock)");
            }
        }

        private void sleep(long ms) {
            try { Thread.sleep(ms); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
        }
    }

    public void startLivelock() {
        PoliteWorker a = new PoliteWorker("Alice");
        PoliteWorker b = new PoliteWorker("Bob");

        Thread t1 = new Thread(() -> a.workWith(b), "Polite-Alice");
        Thread t2 = new Thread(() -> b.workWith(a), "Polite-Bob");

        t1.start();
        t2.start();
    }
}
