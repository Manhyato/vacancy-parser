package com.example.vacancy_parser.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledUpdater {

    private final VacancyService vacancyService;

    public ScheduledUpdater(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    // каждые 5 минут (300000 мс)
    @Scheduled(fixedRate = 300000)
    public void scheduledUpdate() {
        System.out.println(" Плановое обновление вакансий...");
        vacancyService.fetchAndSaveVacancies();
    }
}
