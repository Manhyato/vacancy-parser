package com.example.vacancy_parser;

import com.example.vacancy_parser.deadlock.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Запускает демонстрации Deadlock, Livelock и Starvation.
 */
@Component
public class DemoRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        /* 
        System.out.println("=== Демонстрация Deadlock ===");
        DeadlockDemo deadlockDemo = new DeadlockDemo();
        deadlockDemo.startDeadlock();
        */

        // === После проверки Deadlock закомментируем: ===

        
        System.out.println("=== Демонстрация Livelock ===");
        LivelockDemo livelockDemo = new LivelockDemo();
        livelockDemo.startLivelock();

        Thread.sleep(2000);

        System.out.println("=== Демонстрация Starvation ===");
        StarvationDemo starvationDemo = new StarvationDemo();
        starvationDemo.startStarvation();

        System.out.println("=== Демонстрации завершены ===");
    }
}

