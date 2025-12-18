package com.example.vacancy_parser.service;

import com.example.vacancy_parser.model.VacancyDTO;
import com.example.vacancy_parser.parser.HabrParser;
import com.example.vacancy_parser.parser.HhParser;
import com.example.vacancy_parser.parser.SuperJobParser;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class AsyncParserService {

    private final HhParser hhParser;
    private final HabrParser habrParser;
    private final SuperJobParser superJobParser;
    private final Executor executor;
    private final Tracer tracer;

    public AsyncParserService(HhParser hhParser, HabrParser habrParser,
                              SuperJobParser superJobParser,
                              @Qualifier("parserExecutor") Executor executor,
                              Tracer tracer) {
        this.hhParser = hhParser;
        this.habrParser = habrParser;
        this.superJobParser = superJobParser;
        this.executor = executor;
        this.tracer = tracer;
    }

    public CompletableFuture<List<VacancyDTO>> parseAllSources() {
        Span span = tracer.spanBuilder("parser.parseAllSources").startSpan();
        try (Scope scope = span.makeCurrent()) {
            String query = "java developer";

            CompletableFuture<List<VacancyDTO>> hhFuture =
                    CompletableFuture.supplyAsync(() -> safeParse("hh", hhParser, query), executor);
            CompletableFuture<List<VacancyDTO>> habrFuture =
                    CompletableFuture.supplyAsync(() -> safeParse("habr", habrParser, query), executor);
            CompletableFuture<List<VacancyDTO>> sjFuture =
                    CompletableFuture.supplyAsync(() -> safeParse("superjob", superJobParser, query), executor);

            return CompletableFuture.allOf(hhFuture, habrFuture, sjFuture)
                    .thenApply(v -> {
                        List<VacancyDTO> combined = new ArrayList<>();
                        combined.addAll(hhFuture.join());
                        combined.addAll(habrFuture.join());
                        combined.addAll(sjFuture.join());
                        span.setAttribute("vacancies.total", combined.size());
                        return combined;
                    });
        } finally {
            span.end();
        }
    }

    /**
     * Безопасный запуск отдельного парсера с отловом ошибок.
     */
    private List<VacancyDTO> safeParse(String sourceName, Object parser, String query) {
        Span span = tracer.spanBuilder("parser.parse." + sourceName).startSpan();
        try (Scope scope = span.makeCurrent()) {
            List<VacancyDTO> result = List.of();
            try {
                if (parser instanceof HhParser hh) result = hh.parse(query);
                else if (parser instanceof HabrParser habr) result = habr.parse(query);
                else if (parser instanceof SuperJobParser sj) result = sj.parse(query);
                span.setAttribute("vacancies.count", result.size());
            } catch (IOException e) {
                span.recordException(e);
                System.err.println("Ошибка парсинга: " + parser.getClass().getSimpleName() + " → " + e.getMessage());
            }
            return result;
        } finally {
            span.end();
        }
    }
}






