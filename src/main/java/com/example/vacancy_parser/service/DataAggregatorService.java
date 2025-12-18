package com.example.vacancy_parser.service;

import com.example.vacancy_parser.model.VacancyDTO;
import com.example.vacancy_parser.repository.VacancyRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataAggregatorService {

    private final VacancyRepository vacancyRepository;

    public DataAggregatorService(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    /**
     * Универсальная сортировка вакансий по параметрам:
     * salary, datePosted, company, city
     */
    public List<VacancyDTO> getSortedVacancies(String sortBy) {
        List<VacancyDTO> vacancies = vacancyRepository.findAll().stream()
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

        Comparator<VacancyDTO> comparator;

        switch (sortBy.toLowerCase()) {
            case "salary":
                comparator = Comparator.comparing(VacancyDTO::getSalary, Comparator.nullsLast(String::compareTo));
                break;
            case "company":
                comparator = Comparator.comparing(VacancyDTO::getCompany, Comparator.nullsLast(String::compareTo));
                break;
            case "city":
                comparator = Comparator.comparing(VacancyDTO::getCity, Comparator.nullsLast(String::compareTo));
                break;
            case "dateposted":
            default:
                comparator = Comparator.comparing(VacancyDTO::getDatePosted, Comparator.nullsLast((a, b) -> b.compareTo(a)));
                break;
        }

        return vacancies.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Фильтр по городу
     */
    public List<VacancyDTO> filterByCity(String city) {
        return vacancyRepository.findAll().stream()
                .filter(v -> v.getCity() != null && v.getCity().equalsIgnoreCase(city))
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

    /**
     * Фильтр по компании
     */
    public List<VacancyDTO> filterByCompany(String company) {
        return vacancyRepository.findAll().stream()
                .filter(v -> v.getCompany() != null && v.getCompany().equalsIgnoreCase(company))
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



