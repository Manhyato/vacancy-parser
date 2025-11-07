package com.example.vacancy_parser.service;

import com.example.vacancy_parser.model.VacancyDTO;
import com.example.vacancy_parser.parser.HabrParser;
import com.example.vacancy_parser.parser.HhParser;
import com.example.vacancy_parser.parser.SuperJobParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class AsyncParserService {

    private final HhParser hhParser;
    private final HabrParser habrParser;
    private final SuperJobParser superJobParser;
    private final Executor executor;

    public AsyncParserService(HhParser hhParser, HabrParser habrParser,
                              SuperJobParser superJobParser,
                              @Qualifier("parserExecutor") Executor executor) {
        this.hhParser = hhParser;
        this.habrParser = habrParser;
        this.superJobParser = superJobParser;
        this.executor = executor;
    }

    /**
     * Асинхронный запуск всех парсеров — выполняется в кастомном пуле потоков.
     */
    @Async("parserExecutor")
    public CompletableFuture<List<VacancyDTO>> parseAllSources() {
        String query = "java developer";

        CompletableFuture<List<VacancyDTO>> hhFuture =
                CompletableFuture.supplyAsync(() -> safeParse(hhParser, query), executor);

        CompletableFuture<List<VacancyDTO>> habrFuture =
                CompletableFuture.supplyAsync(() -> safeParse(habrParser, query), executor);

        CompletableFuture<List<VacancyDTO>> sjFuture =
                CompletableFuture.supplyAsync(() -> safeParse(superJobParser, query), executor);

        // Объединяем результаты всех трёх потоков
        return CompletableFuture.allOf(hhFuture, habrFuture, sjFuture)
                .thenApply(v -> {
                    List<VacancyDTO> combined = new ArrayList<>();
                    combined.addAll(hhFuture.join());
                    combined.addAll(habrFuture.join());
                    combined.addAll(sjFuture.join());
                    return combined;
                });
    }

    /**
     * Безопасный запуск отдельного парсера с отловом ошибок.
     */
    private List<VacancyDTO> safeParse(Object parser, String query) {
        try {
            if (parser instanceof HhParser hh) return hh.parse(query);
            if (parser instanceof HabrParser habr) return habr.parse(query);
            if (parser instanceof SuperJobParser sj) return sj.parse(query);
        } catch (IOException e) {
            System.err.println("Ошибка парсинга: " + parser.getClass().getSimpleName() + " → " + e.getMessage());
        }
        return List.of();
    }
}






