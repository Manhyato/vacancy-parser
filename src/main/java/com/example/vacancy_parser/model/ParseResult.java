package com.example.vacancy_parser.model;

import java.util.List;

public class ParseResult {
    private String source;              // Откуда парсили (HH, Habr, SuperJob)
    private List<VacancyDTO> vacancies; // Результаты парсинга

    public ParseResult() {
    }

    public ParseResult(String source, List<VacancyDTO> vacancies) {
        this.source = source;
        this.vacancies = vacancies;
    }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public List<VacancyDTO> getVacancies() { return vacancies; }
    public void setVacancies(List<VacancyDTO> vacancies) { this.vacancies = vacancies; }
}

