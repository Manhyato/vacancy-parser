package com.example.vacancy_parser.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacancies")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;         // Название вакансии
    private String company;       // Компания
    private String city;          // Город
    private String salary;        // Зарплата
    private String requirements;  // Требования
    private LocalDateTime datePosted; // Дата публикации
    private String source;        // Источник (HH, Habr, SuperJob)

    public Vacancy() {
    }

    // Конструктор: порядок полей должен совпадать с использованием в VacancyService
    public Vacancy(String title, String company, String city, String salary,
                   String requirements, LocalDateTime datePosted, String source) {
        this.title = title;
        this.company = company;
        this.city = city;
        this.salary = salary;
        this.requirements = requirements;
        this.datePosted = datePosted; // <- исправлено: присваиваем datePosted (параметр)
        this.source = source;
    }

    // === Геттеры и сеттеры ===
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }

    // Приводим имя геттера/сеттера к getDatePosted()/setDatePosted(...)
    public LocalDateTime getDatePosted() { return datePosted; }
    public void setDatePosted(LocalDateTime datePosted) { this.datePosted = datePosted; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    @Override
    public String toString() {
        return "Vacancy{" +
                "title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", salary='" + salary + '\'' +
                ", datePosted=" + datePosted +
                ", source='" + source + '\'' +
                '}';
    }
}

