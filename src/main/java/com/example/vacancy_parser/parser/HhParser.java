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
public class HhParser implements VacancyParser {

    private static final String BASE_URL = "https://hh.ru/search/vacancy?text=";

    @Override
    public List<VacancyDTO> parse(String searchQuery) throws IOException {
        String url = BASE_URL + searchQuery.replace(" ", "+");
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (compatible; VacancyParserBot/1.0)")
                .timeout(10000)
                .get();

        Elements vacancies = doc.select("div.serp-item");
        List<VacancyDTO> results = new ArrayList<>();

        for (Element el : vacancies) {
            String title = el.select("a.serp-item__title").text();
            String company = el.select("div.vacancy-serp-item-company").text();
            String city = el.select("div[data-qa='vacancy-serp__vacancy-address']").text();
            String salary = el.select("span.bloko-header-section-3").text();
            String requirements = el.select("div.g-user-content").text();
            LocalDate date = LocalDate.now(); // hh скрывает точную дату на выдаче

            results.add(new VacancyDTO(
                    title, company, city, salary, requirements, date, "HH.ru"
            ));
        }

        return results;
    }
}

