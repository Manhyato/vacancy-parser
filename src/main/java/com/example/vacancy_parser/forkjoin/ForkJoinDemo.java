package com.example.vacancy_parser.forkjoin;

import com.example.vacancy_parser.asyncfetch.*;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinDemo {

    public static void runDemo() {
        System.out.println("=== Демонстрация ForkJoin для подсчёта агрегированных элементов ===");

        AsyncDataFetcher fetcher = new AsyncDataFetcher();
        List<String> ids = List.of("P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8");
        List<FinalResult> results = fetcher.fetchAllDataAsync(ids);
        fetcher.shutdown();

        ForkJoinPool pool = new ForkJoinPool(4);
        AggregationCounterTask task = new AggregationCounterTask(
                results,
                0,
                results.size(),
                r -> r.getPrice() > 0  // фильтр: только успешные элементы
        );

        int total = pool.invoke(task);
        System.out.printf("Итоговое количество успешных элементов: %d%n", total);

        pool.shutdown();
        System.out.println("=== Демонстрация ForkJoin завершена ===");
    }
}


