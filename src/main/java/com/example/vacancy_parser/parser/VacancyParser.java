package com.example.vacancy_parser.parser;

import com.example.vacancy_parser.model.VacancyDTO;
import java.io.IOException;
import java.util.List;

public interface VacancyParser {
    List<VacancyDTO> parse(String searchQuery) throws IOException;
}
