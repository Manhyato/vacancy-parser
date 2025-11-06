package com.example.vacancy_parser.async;

import java.util.Arrays;
import java.util.List;

public class UrlTestRunner {

    public static void runDemo() {
        List<String> urls = Arrays.asList(
                "https://api.github.com",
                "https://www.google.com",
                "https://api.ipify.org",
                "https://httpstat.us/200",
                "https://httpstat.us/500",
                "https://httpstat.us/404",
                "https://jsonplaceholder.typicode.com/posts/1",
                "https://dog.ceo/api/breeds/image/random",
                "https://catfact.ninja/fact",
                "https://api.agify.io?name=bob",
                "https://api.genderize.io?name=sarah",
                "https://api.nationalize.io?name=alex",
                "https://httpbin.org/get",
                "https://httpbin.org/delay/2",
                "https://httpbin.org/status/418"
        );

        AsyncHttpRequester requester = new AsyncHttpRequester(5);
        var results = requester.fetchAll(urls);

        System.out.println("=== Результаты запросов ===");
        results.forEach(System.out::println);

        double avg = results.stream()
                .filter(r -> r.getResponseTime() > 0)
                .mapToLong(RequestResult::getResponseTime)
                .average().orElse(0);

        System.out.println("\nСреднее время ответа: " + (long) avg + " мс");
    }
}

