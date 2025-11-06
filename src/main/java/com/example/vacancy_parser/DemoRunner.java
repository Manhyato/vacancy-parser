package com.example.vacancy_parser;

import com.example.vacancy_parser.async.UrlTestRunner;

public class DemoRunner {

    public static void main(String[] args) {
        System.out.println("=== Запуск демонстрации многопоточности и сетевых операций ===\n");

        try {
            // 🔹 Демонстрация асинхронных HTTP-запросов
            System.out.println("=== Демонстрация асинхронных HTTP-запросов через ThreadPoolExecutor ===");
            UrlTestRunner.runDemo();

            System.out.println("\n=== Все демонстрации завершены успешно ===");

        } catch (Exception e) {
            System.err.println("Произошла ошибка во время выполнения демонстрации: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

