package com.example.vacancy_parser;

import com.example.vacancy_parser.virtualthreads.VirtualVsPlatformDemo;

public class DemoRunner {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Запуск всех демонстраций ===\n");
        // Сравнение виртуальных и обычных потоков
        VirtualVsPlatformDemo.main(null);

        System.out.println("\n=== Все демонстрации завершены ===");
    }
}





