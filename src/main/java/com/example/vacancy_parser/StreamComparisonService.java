package com.example.vacancy_parser;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class StreamComparisonService {

    public void runComparison() {
        // 1. Создаём список из 1 000 000 случайных чисел
        List<Integer> numbers = new Random()
                .ints(1_000_000, 1, 1000)
                .boxed()
                .collect(Collectors.toList());

        System.out.println("Данные готовы. Начинаем обработку...");

        // 2. Последовательный Stream
        long startSequential = System.currentTimeMillis();
        long sumSequential = numbers.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(n -> n * 2)
                .sum();
        long timeSequential = System.currentTimeMillis() - startSequential;

        System.out.println("Последовательный stream() завершён.");
        System.out.println("Сумма: " + sumSequential);
        System.out.println("Время выполнения: " + timeSequential + " мс\n");

        // 3. Параллельный Stream
        long startParallel = System.currentTimeMillis();
        long sumParallel = numbers.parallelStream()
                .filter(n -> n % 2 == 0)
                .mapToInt(n -> n * 2)
                .sum();
        long timeParallel = System.currentTimeMillis() - startParallel;

        System.out.println("Параллельный parallelStream() завершён.");
        System.out.println("Сумма: " + sumParallel);
        System.out.println("Время выполнения: " + timeParallel + " мс\n");

        // 4. Сравнение результатов
        System.out.println("=== Сравнение ===");
        System.out.println("Обычный stream: " + timeSequential + " мс");
        System.out.println("Parallel stream: " + timeParallel + " мс");

        if (timeSequential < timeParallel) {
            System.out.println("Вывод: обычный stream() оказался быстрее в этой ситуации.");
        } else {
            System.out.println("Вывод: parallelStream() оказался быстрее в этой ситуации.");
        }
    }
}