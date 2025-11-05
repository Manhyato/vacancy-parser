package com.example.vacancy_parser.buffer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Потокобезопасный буфер фиксированного размера.
 * Использует ReentrantLock и Condition для ожидания и уведомления потоков.
 */
public class BoundedBuffer<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
    }

    // Добавить элемент (producer)
    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                notFull.await(); // буфер полон — ждём
            }
            queue.add(item);
            notEmpty.signal(); // сообщаем consumer'у, что появились данные
        } finally {
            lock.unlock();
        }
    }

    // Извлечь элемент (consumer)
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await(); // буфер пуст — ждём
            }
            T item = queue.poll();
            notFull.signal(); // освободилось место — уведомляем producer'ов
            return item;
        } finally {
            lock.unlock();
        }
    }

    // Для отладки
    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}
