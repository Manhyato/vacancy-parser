package com.example.vacancy_parser.controller;

import com.example.vacancy_parser.model.VacancyDTO;
import com.example.vacancy_parser.service.VacancyService;
import com.example.vacancy_parser.service.DataAggregatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return "index"; // из templates/index.html
    }

    // API эндпоинты
    @PostMapping("/api/vacancies/parse")
    @ResponseBody
    public ResponseEntity<String> parseVacancies() {
        vacancyService.fetchAndSaveVacancies();
        return ResponseEntity.ok("Парсинг запущен и данные сохранены в базу данных.");
    }

    @GetMapping("/api/vacancies/results")
    @ResponseBody
    public List<VacancyDTO> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    @GetMapping("/api/vacancies/sorted")
    @ResponseBody
    public List<VacancyDTO> getSortedVacancies(@RequestParam(defaultValue = "datePosted") String sortBy) {
        return dataAggregatorService.getSortedVacancies(sortBy);
    }

    @PutMapping("/api/vacancies/update")
    @ResponseBody
    public String updateVacancies() {
        vacancyService.fetchAndSaveVacancies();
        return "Обновление вакансий выполнено успешно.";
    }
}


