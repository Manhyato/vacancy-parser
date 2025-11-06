package com.example.vacancy_parser.async;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class AsyncHttpRequester {

    private final ThreadPoolExecutor executor;
    private final HttpClient httpClient;

    public AsyncHttpRequester(int poolSize) {
        this.executor = new ThreadPoolExecutor(
                poolSize, poolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    public List<RequestResult> fetchAll(List<String> urls) {
        List<Future<RequestResult>> futures = new ArrayList<>();
        for (String url : urls) {
            futures.add(executor.submit(() -> fetch(url)));
        }

        List<RequestResult> results = new ArrayList<>();
        for (Future<RequestResult> f : futures) {
            try {
                results.add(f.get());
            } catch (Exception e) {
                results.add(new RequestResult("ERROR", -1, -1));
            }
        }

        executor.shutdown();
        return results;
    }

    private RequestResult fetch(String url) {
        long start = System.currentTimeMillis();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();
            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            long duration = System.currentTimeMillis() - start;
            return new RequestResult(url, response.statusCode(), duration);
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - start;
            return new RequestResult(url, -1, duration);
        }
    }
}
