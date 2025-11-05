package com.example.vacancy_parser.bank;

import java.util.List;
import java.util.Random;

public class TransferTask implements Runnable {
    private final AccountService accountService;
    private final List<Account> accounts;
    private final int transfers;
    private final Random rnd = new Random();

    public TransferTask(AccountService accountService, List<Account> accounts, int transfers) {
        this.accountService = accountService;
        this.accounts = accounts;
        this.transfers = transfers;
    }

    @Override
    public void run() {
        int n = accounts.size();
        for (int i = 0; i < transfers; i++) {
            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);
            if (a == b) continue;
            Account src = accounts.get(a);
            Account dst = accounts.get(b);
            long amount = rnd.nextInt(50); // небольшая сумма
            accountService.transfer(src, dst, amount);
        }
    }
}