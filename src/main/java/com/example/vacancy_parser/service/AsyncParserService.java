package com.example.vacancy_parser.service;

import com.example.vacancy_parser.model.VacancyDTO;
import com.example.vacancy_parser.parser.HhParser;
import com.example.vacancy_parser.parser.HabrParser;
import com.example.vacancy_parser.parser.SuperJobParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AsyncParserService {

    private final HhParser hhParser;
    private final HabrParser habrParser;
    private final SuperJobParser superJobParser;

    public AsyncParserService(HhParser hhParser, HabrParser habrParser, SuperJobParser superJobParser) {
        this.hhParser = hhParser;
        this.habrParser = habrParser;
        this.superJobParser = superJobParser;
    }

    public List<VacancyDTO> parseAllSources() {
        List<VacancyDTO> all = new ArrayList<>();

        try {
            all.addAll(hhParser.parse("java developer"));
        } catch (IOException e) {
            System.err.println("Ошибка парсинга HH: " + e.getMessage());
        }

        try {
            all.addAll(habrParser.parse("java developer"));
        } catch (IOException e) {
            System.err.println("Ошибка парсинга Habr Career: " + e.getMessage());
        }

        try {
            all.addAll(superJobParser.parse("java developer"));
        } catch (IOException e) {
            System.err.println("Ошибка парсинга SuperJob: " + e.getMessage());
        }

        return all;
    }
}



