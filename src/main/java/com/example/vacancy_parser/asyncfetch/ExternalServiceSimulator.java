package com.example.vacancy_parser.asyncfetch;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ExternalServiceSimulator {
    private static final Random random = new Random();

    public static String fetchName(String id) throws Exception {
        TimeUnit.MILLISECONDS.sleep(300 + random.nextInt(700));
        if (id.equals("P3")) throw new Exception("Ошибка при получении имени для " + id);
        return "Product-" + id;
    }

    public static double fetchPrice(String id) throws Exception {
        TimeUnit.MILLISECONDS.sleep(500 + random.nextInt(1000));
        if (id.equals("P5")) throw new Exception("Ошибка при получении цены для " + id);
        return 100 + random.nextDouble() * 500;
    }

    public static double fetchRate() {
        return 0.9 + random.nextDouble() * 0.3; // курс валюты
    }
}

