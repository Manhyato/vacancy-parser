package com.example.vacancy_parser.controller;

import com.example.vacancy_parser.model.VacancyDTO;
import com.example.vacancy_parser.service.VacancyService;
import com.example.vacancy_parser.service.DataAggregatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VacancyController {

    private final VacancyService vacancyService;
    private final DataAggregatorService dataAggregatorService;

    public VacancyController(VacancyService vacancyService, DataAggregatorService dataAggregatorService) {
        this.vacancyService = vacancyService;
        this.dataAggregatorService = dataAggregatorService;
    }

    // Главная страница
    @GetMapping("/")
    public String home(Model model) {
        List<VacancyDTO> vacancies = vacancyService.getAllVacancies();
        model.addAttribute("vacancies", vacancies);
        return "index";
    }

    // Запуск парсинга вручную
    @PostMapping("/api/vacancies/parse")
    @ResponseBody
    public ResponseEntity<String> parseVacancies() {
        vacancyService.fetchAndSaveVacancies();
        return ResponseEntity.ok("Парсинг запущен и данные сохранены в базу данных.");
    }

    // Универсальный эндпоинт: фильтрация + сортировка
    @GetMapping("/api/vacancies/results")
    @ResponseBody
    public List<VacancyDTO> getFilteredAndSortedVacancies(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String company,
            @RequestParam(defaultValue = "datePosted") String sortBy) {

        List<VacancyDTO> vacancies = vacancyService.getAllVacancies();

        if (city != null && !city.isEmpty()) {
            vacancies = vacancies.stream()
                    .filter(v -> v.getCity() != null && v.getCity().equalsIgnoreCase(city))
                    .collect(Collectors.toList());
        }

        if (company != null && !company.isEmpty()) {
            vacancies = vacancies.stream()
                    .filter(v -> v.getCompany() != null && v.getCompany().equalsIgnoreCase(company))
                    .collect(Collectors.toList());
        }

        Comparator<VacancyDTO> comparator;

        switch (sortBy.toLowerCase()) {
            case "salary":
                comparator = Comparator.comparing(VacancyDTO::getSalary,
                        Comparator.nullsLast(String::compareTo));
                break;
            case "company":
                comparator = Comparator.comparing(VacancyDTO::getCompany,
                        Comparator.nullsLast(String::compareTo));
                break;
            case "city":
                comparator = Comparator.comparing(VacancyDTO::getCity,
                        Comparator.nullsLast(String::compareTo));
                break;
            case "dateposted":
            default:
                comparator = Comparator.comparing(VacancyDTO::getDatePosted,
                        Comparator.nullsLast((a, b) -> b.compareTo(a)));
                break;
        }

        return vacancies.stream().sorted(comparator).collect(Collectors.toList());
    }
}




