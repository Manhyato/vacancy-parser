package com.example.vacancy_parser.bank;

public class Account {
    private final long id;
    private long balance;

    public Account(long id, long initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public long getId() { return id; }

    // всегда вызывать в синхронизированном контексте на самом account
    public long getBalance() { return balance; }

    public void deposit(long amount) {
        balance += amount;
    }

    public void withdraw(long amount) {
        balance -= amount;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", balance=" + balance + '}';
    }
}
