package com.example.vacancy_parser.repository;

import com.example.vacancy_parser.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    // Поиск по названию вакансии 
    List<Vacancy> findByTitleContainingIgnoreCase(String title);

    // Поиск по городу
    List<Vacancy> findByCityIgnoreCase(String city);

    // Поиск по компании
    List<Vacancy> findByCompanyIgnoreCase(String company);
}

