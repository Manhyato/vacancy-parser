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
public class SuperJobParser implements VacancyParser {

    private static final String BASE_URL = "https://russia.superjob.ru/vacancy/search/?keywords=";

    @Override
    public List<VacancyDTO> parse(String searchQuery) throws IOException {
        String url = BASE_URL + searchQuery.replace(" ", "+");
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (compatible; VacancyParserBot/1.0)")
                .timeout(10000)
                .get();

        Elements vacancies = doc.select("div.f-test-search-result-item");
        List<VacancyDTO> results = new ArrayList<>();

        for (Element el : vacancies) {
            String title = el.select("span.f-test-text-vacancy-item-title").text();
            String company = el.select("span.f-test-text-vacancy-item-company-name").text();
            String city = el.select("span.f-test-text-company-item-location").text();
            String salary = el.select("span.f-test-text-company-item-salary").text();
            String requirements = el.select("div._3mfro._9fXTd._2JVkc._3e53o").text();
            LocalDate date = LocalDate.now();

            results.add(new VacancyDTO(
                    title, company, city, salary, requirements, date, "SuperJob"
            ));
        }

        return results;
    }
}

