package com.example.vacancy_parser.concurrency;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Потокобезопасный singleton-кэш с использованием AtomicReference.
 */
public class AtomicCache {

    private final AtomicReference<String> cachedValue = new AtomicReference<>(null);

    public String getOrCreate(String newValue) {
        // если значение пустое, устанавливаем новое (один раз)
        cachedValue.compareAndSet(null, newValue);
        return cachedValue.get();
    }

    public String getValue() {
        return cachedValue.get();
    }
}
