package com.example.vacancy_parser.bank;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    /**
     * Перевод с source -> target на amount. Для предотвращения deadlock
     * всегда синхронизируемся на аккаунтах в порядке возрастания их id.
     */
    public void transfer(Account source, Account target, long amount) {
        Account first = source.getId() < target.getId() ? source : target;
        Account second = source.getId() < target.getId() ? target : source;

        synchronized (first) {
            // небольшая задержка для усиления шанса гонки (для демонстрации/тестов)
            synchronized (second) {
                // проверка баланса и выполнение перевода
                if (source.getBalance() < amount) {
                    // недостаточно средств — ничего не делаем
                    return;
                }
                source.withdraw(amount);
                target.deposit(amount);
            }
        }
    }
}
