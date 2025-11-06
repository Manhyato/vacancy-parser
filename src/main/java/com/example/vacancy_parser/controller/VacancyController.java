package com.example.vacancy_parser.controller;

import com.example.vacancy_parser.model.VacancyDTO;
import com.example.vacancy_parser.service.VacancyService;
import com.example.vacancy_parser.service.DataAggregatorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST контроллер для работы с вакансиями:
 * - запуск парсинга вручную
 * - получение сохранённых данных
 * - получение агрегированных / отсортированных результатов
 */
@RestController
@RequestMapping("/api/vacancies")
public class VacancyController {

    private final VacancyService vacancyService;
    private final DataAggregatorService dataAggregatorService;

    public VacancyController(VacancyService vacancyService, DataAggregatorService dataAggregatorService) {
        this.vacancyService = vacancyService;
        this.dataAggregatorService = dataAggregatorService;
    }

    /**
     * Ручной запуск парсинга вакансий (асинхронно)
     */
    @PostMapping("/parse")
    public String parseVacancies() {
        vacancyService.fetchAndSaveVacancies();
        return "Парсинг запущен и данные сохранены в базу.";
    }

    /**
     * Получить все вакансии из базы
     */
    @GetMapping("/results")
    public List<VacancyDTO> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    /**
     * Получить агрегированные и отсортированные вакансии
     * (пример: /api/vacancies/sorted?sortBy=salary)
     */
    @GetMapping("/sorted")
    public List<VacancyDTO> getSortedVacancies(@RequestParam(defaultValue = "datePosted") String sortBy) {
        return dataAggregatorService.getSortedVacancies(sortBy);
    }

    /**
     * Обновить данные вручную (например, если нужно перезапустить по расписанию)
     */
    @PutMapping("/update")
    public String updateVacancies() {
        vacancyService.fetchAndSaveVacancies();
        return "Обновление вакансий выполнено успешно.";
    }
}

