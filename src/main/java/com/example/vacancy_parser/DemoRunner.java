package com.example.vacancy_parser;

import com.example.vacancy_parser.bank.Account;
import com.example.vacancy_parser.bank.AccountService;
import com.example.vacancy_parser.bank.TransferTask;
import com.example.vacancy_parser.concurrent.ConsumerTask;
import com.example.vacancy_parser.concurrent.DataCollector;
import com.example.vacancy_parser.concurrent.ProducerTask;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
public class DemoRunner implements CommandLineRunner {

    private final DataCollector collector;
    private final AccountService accountService;

    public DemoRunner(DataCollector collector, AccountService accountService) {
        this.collector = collector;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Запуск демо DataCollector ===");
        runDataCollectorDemo();

        System.out.println("\n=== Запуск демо Account transfers ===");
        runAccountDemo();
    }

    private void runDataCollectorDemo() throws InterruptedException {
        int producers = 4;
        int consumers = 3;
        int itemsEach = 500; // всего 2000 элементов
        ExecutorService prodPool = Executors.newFixedThreadPool(producers);
        ExecutorService consPool = Executors.newFixedThreadPool(consumers);

        // старт consumers
        List<ConsumerTask> consumerTasks = new ArrayList<>();
        for (int i = 0; i < consumers; i++) {
            ConsumerTask ct = new ConsumerTask(collector, i);
            consumerTasks.add(ct);
            consPool.submit(ct);
        }

        long t0 = System.currentTimeMillis();
        for (int i = 0; i < producers; i++) {
            prodPool.submit(new ProducerTask(collector, i, itemsEach));
        }

        prodPool.shutdown();
        prodPool.awaitTermination(30, TimeUnit.SECONDS);

        // Дадим немного времени на обработку
        Thread.sleep(2000);

        // остановим consumers корректно
        for (ConsumerTask ct : consumerTasks) ct.stop();
        consPool.shutdown();
        consPool.awaitTermination(10, TimeUnit.SECONDS);

        long t1 = System.currentTimeMillis();
        System.out.println("DataCollector snapshot: " + collector.snapshot());
        System.out.println("Время выполнения (производители+потребители): " + (t1 - t0) + " ms");
    }

    private void runAccountDemo() throws InterruptedException {
        int accountsCount = 10;
        int initial = 1000;
        List<Account> accounts = new ArrayList<>();
        long total = 0;
        for (int i = 0; i < accountsCount; i++) {
            accounts.add(new Account(i + 1, initial));
            total += initial;
        }

        int transferThreads = 8;
        int transfersPerThread = 5000;
        ExecutorService pool = Executors.newFixedThreadPool(transferThreads);
        long t0 = System.currentTimeMillis();
        for (int i = 0; i < transferThreads; i++) {
            pool.submit(new TransferTask(accountService, accounts, transfersPerThread));
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        long t1 = System.currentTimeMillis();

        // проверка целостности: сумма балансов должна равняться total
        long sum = accounts.stream().mapToLong(Account::getBalance).sum();
        System.out.println("Ожидаемая суммарная сумма: " + total + ", реальная: " + sum);
        System.out.println("Время переводов: " + (t1 - t0) + " ms");
    }
}
