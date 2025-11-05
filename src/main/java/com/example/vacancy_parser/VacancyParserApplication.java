package com.example.vacancy_parser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VacancyParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(VacancyParserApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(StreamComparisonService service) {
        return args -> service.runComparison();
    }
}