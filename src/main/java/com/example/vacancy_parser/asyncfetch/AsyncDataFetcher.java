package com.example.vacancy_parser.asyncfetch;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class AsyncDataFetcher {

    private final ExecutorService executor = Executors.newFixedThreadPool(
            5,
            r -> {
                Thread t = new Thread(r);
                t.setName("async-fetcher-" + t.getId());
                t.setDaemon(true);
                return t;
            }
    );

    public List<FinalResult> fetchAllDataAsync(List<String> ids) {
        List<CompletableFuture<FinalResult>> futures = ids.stream()
                .map(id -> {
                    CompletableFuture<String> nameFuture =
                            CompletableFuture.supplyAsync(() -> {
                                try {
                                    return ExternalServiceSimulator.fetchName(id);
                                } catch (Exception e) {
                                    System.err.println(Thread.currentThread().getName() + " — Ошибка имени: " + e.getMessage());
                                    return "N/A";
                                }
                            }, executor);

                    CompletableFuture<Double> priceFuture =
                            CompletableFuture.supplyAsync(() -> {
                                try {
                                    return ExternalServiceSimulator.fetchPrice(id);
                                } catch (Exception e) {
                                    System.err.println(Thread.currentThread().getName() + " — Ошибка цены: " + e.getMessage());
                                    return -1.0;
                                }
                            }, executor);

                    CompletableFuture<Double> rateFuture =
                            CompletableFuture.supplyAsync(ExternalServiceSimulator::fetchRate, executor);

                    return nameFuture
                            .thenCombine(priceFuture, (name, price) ->
                                    new FinalResult(id, name, price, 1.0))
                            .thenCombine(rateFuture, (res, rate) ->
                                    new FinalResult(res.toString(), res.toString(), res.getPrice(), rate))
                            .exceptionally(ex -> {
                                System.err.println("Ошибка обработки для " + id + ": " + ex.getMessage());
                                return new FinalResult(id, "N/A", -1, -1);
                            });
                })
                .collect(Collectors.toList());

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public void shutdown() {
        executor.shutdown();
    }
}

