package com.example.vacancy_parser.service;

import com.example.vacancy_parser.model.Vacancy;
import com.example.vacancy_parser.model.VacancyDTO;
import com.example.vacancy_parser.repository.VacancyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacancyService {

    private final AsyncParserService asyncParserService;
    private final VacancyRepository vacancyRepository;

    public VacancyService(AsyncParserService asyncParserService, VacancyRepository vacancyRepository) {
        this.asyncParserService = asyncParserService;
        this.vacancyRepository = vacancyRepository;
    }

    @Transactional
    public void fetchAndSaveVacancies() {
        vacancyRepository.deleteAll();
        System.out.println("Все старые вакансии удалены.");
        List<VacancyDTO> dtos = asyncParserService.parseAllSources();

            List<Vacancy> entities = dtos.stream()
                .map(dto -> new Vacancy(
                dto.getTitle(),
                dto.getCompany(),
                dto.getCity(),          
                dto.getSalary(),
                dto.getRequirements(),
                dto.getDatePosted(),
                dto.getSource()         
                ))
            .collect(Collectors.toList());

        vacancyRepository.saveAll(entities);
        System.out.println("Обновление завершено. Сохранено " + entities.size() + " вакансий в базу данных.");
    }

    public List<VacancyDTO> getAllVacancies() {
        return vacancyRepository.findAll().stream()
                .map(v -> new VacancyDTO(
                    v.getTitle(),
                    v.getCompany(),
                    v.getCity(),          
                    v.getSalary(),
                    v.getRequirements(),
                    v.getDatePosted(),    
                    v.getSource()         
))
                .collect(Collectors.toList());
    }
}


