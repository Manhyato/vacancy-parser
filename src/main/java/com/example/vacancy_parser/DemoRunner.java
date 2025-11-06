package com.example.vacancy_parser;

import com.example.vacancy_parser.concurrentcache.EventSystemDemo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Демонстрация многопоточной системы событий ===");
        EventSystemDemo.runDemo();
        System.out.println("=== Демонстрация завершена ===");
    }
}




