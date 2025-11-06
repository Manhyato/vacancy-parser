package com.example.vacancy_parser.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VacancyDTO {
    private String title;
    private String company;
    private String city;
    private String salary;
    private String requirements;
    private LocalDateTime datePosted;
    private String source;

    public VacancyDTO() {
    }

    // Основной конструктор с LocalDateTime
    public VacancyDTO(String title, String company, String city, String salary,
                      String requirements, LocalDateTime datePosted, String source) {
        this.title = title;
        this.company = company;
        this.city = city;
        this.salary = salary;
        this.requirements = requirements;
        this.datePosted = datePosted;
        this.source = source;
    }

    // Удобный конструктор: принимаем LocalDate и конвертируем в LocalDateTime
    public VacancyDTO(String title, String company, String city, String salary,
                      String requirements, LocalDate postedDate, String source) {
        this(title, company, city, salary, requirements,
                postedDate != null ? postedDate.atStartOfDay() : null, source);
    }

    // Упрощённый конструктор без source
    public VacancyDTO(String title, String company, String city, String salary,
                      String requirements, LocalDate postedDate) {
        this(title, company, city, salary, requirements,
                postedDate != null ? postedDate.atStartOfDay() : null, null);
    }

    // ===== Геттеры и сеттеры =====
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

    // Геттер/сеттер с именем getDatePosted / setDatePosted
    public LocalDateTime getDatePosted() { return datePosted; }
    public void setDatePosted(LocalDateTime datePosted) { this.datePosted = datePosted; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    @Override
    public String toString() {
        return "VacancyDTO{" +
                "title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", salary='" + salary + '\'' +
                ", requirements='" + requirements + '\'' +
                ", datePosted=" + datePosted +
                ", source='" + source + '\'' +
                '}';
    }
}




