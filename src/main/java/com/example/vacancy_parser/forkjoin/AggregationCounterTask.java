package com.example.vacancy_parser.forkjoin;

import com.example.vacancy_parser.asyncfetch.FinalResult;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class AggregationCounterTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 3; // размер батча
    private final List<FinalResult> results;
    private final int start;
    private final int end;
    private final Predicate<FinalResult> filter;

    public AggregationCounterTask(List<FinalResult> results, int start, int end, Predicate<FinalResult> filter) {
        this.results = results;
        this.start = start;
        this.end = end;
        this.filter = filter;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            int count = 0;
            for (int i = start; i < end; i++) {
                FinalResult r = results.get(i);
                if (filter.test(r)) {
                    count++;
                }
            }
            System.out.printf("[%s] Обработан диапазон %d – %d → %d%n",
                    Thread.currentThread().getName(), start, end, count);
            return count;
        } else {
            int mid = (start + end) / 2;
            AggregationCounterTask left = new AggregationCounterTask(results, start, mid, filter);
            AggregationCounterTask right = new AggregationCounterTask(results, mid, end, filter);

            left.fork();
            int rightCount = right.compute();
            int leftCount = left.join();

            return leftCount + rightCount;
        }
    }
}

