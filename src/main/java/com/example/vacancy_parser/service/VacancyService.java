package com.example.vacancy_parser.service;

import com.example.vacancy_parser.model.Vacancy;
import com.example.vacancy_parser.model.VacancyDTO;
import com.example.vacancy_parser.repository.VacancyRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class VacancyService {

    private final AsyncParserService asyncParserService;
    private final VacancyRepository vacancyRepository;
    private final Tracer tracer;
    private final Timer parsingTimer;
    private final Counter successfulParsingCounter;
    private final Counter failedParsingCounter;
    private final Counter savedRecordsCounter;

    public VacancyService(AsyncParserService asyncParserService,
                          VacancyRepository vacancyRepository,
                          MeterRegistry meterRegistry,
                          Tracer tracer) {
        this.asyncParserService = asyncParserService;
        this.vacancyRepository = vacancyRepository;
        this.tracer = tracer;

        this.parsingTimer = Timer.builder("vacancy_parser.parsing.duration")
                .description("Время выполнения полного цикла парсинга и сохранения вакансий")
                .publishPercentiles(0.5, 0.95, 0.99)
                .register(meterRegistry);

        this.successfulParsingCounter = Counter.builder("vacancy_parser.parsing.success.count")
                .description("Количество успешных запусков парсинга")
                .register(meterRegistry);

        this.failedParsingCounter = Counter.builder("vacancy_parser.parsing.error.count")
                .description("Количество неуспешных запусков парсинга")
                .register(meterRegistry);

        this.savedRecordsCounter = Counter.builder("vacancy_parser.db.saved.records.count")
                .description("Общее количество записей, сохранённых в базу данных")
                .register(meterRegistry);
    }

    @Transactional
    public void fetchAndSaveVacancies() {
        Span span = tracer.spanBuilder("vacancy.fetchAndSave").startSpan();
        long startNanos = System.nanoTime();
        boolean success = false;
        int savedCount = 0;
        try (Scope scope = span.makeCurrent()) {
            vacancyRepository.deleteAll();
            System.out.println("Все старые вакансии удалены.");

            List<VacancyDTO> dtos = asyncParserService.parseAllSources().join();
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
            savedCount = entities.size();
            System.out.println("Обновление завершено. Сохранено " + savedCount + " вакансий в базу данных.");
            success = true;
            span.setAttribute("vacancies.saved", savedCount);
        } catch (RuntimeException ex) {
            System.err.println("Ошибка при обновлении вакансий: " + ex.getMessage());
            failedParsingCounter.increment();
            span.recordException(ex);
            span.setStatus(StatusCode.ERROR);
            throw ex;
        } finally {
            long durationNanos = System.nanoTime() - startNanos;
            parsingTimer.record(durationNanos, TimeUnit.NANOSECONDS);
            if (success) {
                successfulParsingCounter.increment();
                if (savedCount > 0) {
                    savedRecordsCounter.increment(savedCount);
                }
            }
            span.end();
        }
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


