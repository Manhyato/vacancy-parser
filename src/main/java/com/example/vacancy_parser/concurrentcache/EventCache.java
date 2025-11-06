package com.example.vacancy_parser.concurrentcache;

import java.util.concurrent.*;

public class EventCache {
    private final ConcurrentMap<String, Event> cache = new ConcurrentHashMap<>();

    public void put(String key, Event event) {
        cache.put(key, event);
    }

    public Event get(String key) {
        return cache.get(key);
    }

    public int size() {
        return cache.size();
    }

    public void printAll() {
        cache.forEach((k, v) -> System.out.println(k + " → " + v));
    }
}

