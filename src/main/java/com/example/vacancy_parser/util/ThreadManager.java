package com.example.vacancy_parser.util;

import com.example.vacancy_parser.service.SimpleService;
import com.example.vacancy_parser.task.LoggerThread;
import com.example.vacancy_parser.task.TaskRunnable;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ThreadManager {

    private final SimpleService service;

    public ThreadManager(SimpleService service) {
        this.service = service;
    }

    public void startDemo() throws InterruptedException {
        TaskRunnable task = new TaskRunnable(service, 10);
        Thread t1 = new Thread(task, "CounterWorker");

        LoggerThread logger = new LoggerThread(8);

        System.out.println("Starting threads...");
        t1.start();
        logger.start();

        t1.join();
        logger.join();

        System.out.println("Threads finished. Active threads list:");
        printAllActiveThreads();     
    }
    
    private void printAllActiveThreads() {
        // Получим все живые потоки через getAllStackTraces
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        threads.stream()
        .filter(Thread::isAlive)
        .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
        .forEach(t -> System.out.println("- " + t.getName() + " (id=" + t.getId() + ") state=" + t.getState()));
    }
}
