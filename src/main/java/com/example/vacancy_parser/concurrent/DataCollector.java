package com.example.vacancy_parser.concurrent;

import com.example.vacancy_parser.model.Item;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataCollector {

    private final List<Item> items = new ArrayList<>(); // защищённый список
    private final Set<String> processedKeys = new HashSet<>(); // чтобы не дублировать
    private long processedCount = 0L;

    // добавляем элемент (producer). синхронизируем доступ
    public synchronized void collectItem(Item item) {
        // если уже обработан, игнорируем
        if (processedKeys.contains(item.getKey())) {
            return;
        }
        items.add(item);
        // уведомляем всех waiting-потоков, что появились данные
        notifyAll();
    }

    // consumer: взять элемент (или ждать, если списка нет)
    public synchronized Item takeItemOrWait() throws InterruptedException {
        while (items.isEmpty()) {
            wait(); // ждём notify/notifyAll от producer
        }
        return items.remove(0);
    }

    // безопасно увеличиваем счетчик
    public synchronized void incrementProcessed() {
        processedCount++;
    }

    public synchronized long getProcessedCount() {
        return processedCount;
    }

    // проверка по ключу — синхронный доступ к множеству processedKeys
    public synchronized boolean isAlreadyProcessed(String key) {
        return processedKeys.contains(key);
    }

    // отметить как обработанное (атомарно вместе с увеличением счётчика)
    public synchronized void markProcessed(Item item) {
        if (!processedKeys.contains(item.getKey())) {
            processedKeys.add(item.getKey());
            processedCount++;
        }
    }

    // Для мониторинга — скопировать текущее состояние (без внешней мутации)
    public synchronized Map<String, Object> snapshot() {
        Map<String, Object> s = new HashMap<>();
        s.put("queueSize", items.size());
        s.put("processedCount", processedCount);
        s.put("uniqueProcessedKeys", processedKeys.size());
        return s;
    }
}
