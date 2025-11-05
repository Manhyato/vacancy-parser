package com.example.vacancy_parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс Spring Boot приложения.
 * Здесь только старт приложения, вся логика выполняется в DemoRunner.
 */
@SpringBootApplication
public class VacancyParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(VacancyParserApplication.class, args);
    }
}