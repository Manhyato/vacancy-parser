package com.example.vacancy_parser;

import com.example.vacancy_parser.executor.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println("=== Запуск демонстрации ExecutorService ===\n");

        FutureDemo.runDemo();
        InvokeAllDemo.runDemo();
        ScheduledDemo.runDemo();

        PeriodicDataAggregator aggregator = new PeriodicDataAggregator();
        aggregator.startAggregation();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            aggregator.shutdown();
            System.out.println("\n=== Все демонстрации завершены ===");
        }
    }
}


