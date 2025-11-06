package com.example.vacancy_parser.virtualthreads;

public class TaskWorker {

    public static void execute(String taskName) {
        try {
            // Имитация неблокирующей I/O операции
            Thread.sleep((int) (Math.random() * 20)); // 0–20 мс
            if (Math.random() < 0.0005) { // имитация редкой ошибки
                throw new RuntimeException("Ошибка обработки " + taskName);
            }
            System.out.println(Thread.currentThread().getName() + " выполняет " + taskName);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("❌ " + e.getMessage());
        }
    }
}

