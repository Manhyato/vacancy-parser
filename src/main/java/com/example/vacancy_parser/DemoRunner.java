package com.example.vacancy_parser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.vacancy_parser.asyncfetch.AsyncDataFetcher;
import com.example.vacancy_parser.asyncfetch.FinalResult;
/* import com.example.vacancy_parser.asyncfetch.ExternalServiceSimulator; */

import java.util.Arrays;
import java.util.List;

@Component
public class DemoRunner implements CommandLineRunner {

    private final AsyncDataFetcher dataFetcher;

    public DemoRunner(AsyncDataFetcher dataFetcher) {
        this.dataFetcher = dataFetcher;
    }

    @Override
    public void run(String... args) {
        System.out.println("=== Демонстрация CompletableFuture с собственным Executor ===");

        List<String> ids = Arrays.asList("P1", "P2", "P3", "P4", "P5");
        long start = System.currentTimeMillis();

        List<FinalResult> results = dataFetcher.fetchAllDataAsync(ids);

        results.forEach(System.out::println);

        System.out.println("Время выполнения: " + (System.currentTimeMillis() - start) + " мс");
        dataFetcher.shutdown();

        System.out.println("=== Демонстрация завершена ===");
    }
}



