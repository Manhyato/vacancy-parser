package com.example.vacancy_parser.parser;

import com.example.vacancy_parser.model.VacancyDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class HhParser implements VacancyParser {

    private static final String API_URL = "https://api.hh.ru/vacancies?text=%s&area=1&page=0&per_page=20";

    @Override
    public List<VacancyDTO> parse(String searchQuery) throws IOException {
        String urlStr = String.format(API_URL, searchQuery.replace(" ", "+"));
        URL url = new URL(urlStr);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; VacancyParserBot/1.0)");
        connection.setRequestMethod("GET");

        // Читаем ответ JSON
        StringBuilder jsonText = new StringBuilder();
        try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8)) {
            while (scanner.hasNext()) {
                jsonText.append(scanner.nextLine());
            }
        }

        // Разбираем JSON
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonText.toString());
        JsonNode items = root.path("items");

        List<VacancyDTO> results = new ArrayList<>();
        for (JsonNode item : items) {
            String title = item.path("name").asText();
            String company = item.path("employer").path("name").asText();
            String city = item.path("area").path("name").asText();
            String salary = "";

            if (item.has("salary") && !item.path("salary").isNull()) {
                JsonNode s = item.path("salary");
                String from = s.path("from").isMissingNode() || s.path("from").isNull() ? "" : s.path("from").asText();
                String to = s.path("to").isMissingNode() || s.path("to").isNull() ? "" : s.path("to").asText();
                String currency = s.path("currency").isMissingNode() || s.path("currency").isNull() ? "" : s.path("currency").asText();
                salary = (from + " - " + to + " " + currency).trim();
            }

            String requirements = item.path("snippet").path("requirement").asText();
            LocalDate date = LocalDate.now();

            results.add(new VacancyDTO(
                    title, company, city, salary, requirements, date, "HH.ru"
            ));
        }

        return results;
    }
}


