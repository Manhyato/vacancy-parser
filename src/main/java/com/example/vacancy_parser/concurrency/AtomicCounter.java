package com.example.vacancy_parser.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Потокобезопасный счетчик с использованием AtomicInteger.
 */
public class AtomicCounter {

    private final AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.incrementAndGet(); // атомарное увеличение
    }

    public int getValue() {
        return counter.get();
    }
}


