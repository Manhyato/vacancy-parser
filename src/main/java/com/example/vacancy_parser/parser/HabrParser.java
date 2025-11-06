package com.example.vacancy_parser.parser;

import com.example.vacancy_parser.model.VacancyDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class HabrParser implements VacancyParser {

    private static final String BASE_URL = "https://career.habr.com/vacancies?query=";

    @Override
    public List<VacancyDTO> parse(String searchQuery) throws IOException {
        String url = BASE_URL + searchQuery.replace(" ", "+");
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (compatible; VacancyParserBot/1.0)")
                .timeout(10000)
                .get();

        Elements vacancies = doc.select("div.vacancy-card");
        List<VacancyDTO> results = new ArrayList<>();

        for (Element el : vacancies) {
            String title = el.select("div.vacancy-card__title").text();
            String company = el.select("div.vacancy-card__company-title").text();
            String city = el.select("span.vacancy-card__meta").text();
            String salary = el.select("div.vacancy-card__salary").text();
            String requirements = el.select("div.vacancy-card__skills").text();
            LocalDate date = LocalDate.now();

            results.add(new VacancyDTO(
                    title, company, city, salary, requirements, date, "Habr Career"
            ));
        }

        return results;
    }
}


