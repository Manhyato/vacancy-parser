package com.example.vacancy_parser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.vacancy_parser.asyncfetch.AsyncDataFetcher;
import com.example.vacancy_parser.asyncfetch.FinalResult;

import com.example.vacancy_parser.forkjoin.ForkJoinDemo;

public class DemoRunner {

    public static void main(String[] args) {
        System.out.println("=== Запуск всех демонстраций ===\n");

        // 1. Асинхронная выборка данных (предыдущее задание)
        AsyncDataFetcher fetcher = new AsyncDataFetcher();
        fetcher.fetchAllDataAsync(java.util.List.of("P1", "P2", "P3", "P4", "P5"));
        fetcher.shutdown();

        // 2. Подсчёт агрегированных элементов через ForkJoin
        ForkJoinDemo.runDemo();

        System.out.println("\n=== Все демонстрации завершены ===");
    }
}




