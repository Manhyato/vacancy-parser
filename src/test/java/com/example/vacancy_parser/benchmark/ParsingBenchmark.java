package com.example.vacancy_parser.benchmark;

import com.example.vacancy_parser.model.Vacancy;
import com.example.vacancy_parser.model.VacancyDTO;
import org.openjdk.jmh.annotations.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(0)
@State(Scope.Thread)
public class ParsingBenchmark {

    @Param({"100", "1000"})
    private int size;

    private List<VacancyDTO> source;

    @Setup
    public void setup() {
        source = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            source.add(new VacancyDTO(
                    "Java Developer " + i,
                    "Company " + i,
                    "City",
                    "100000",
                    "Requirements",
                    LocalDate.now(),
                    "Mock"
            ));
        }
    }

    @Benchmark
    public List<Vacancy> mapWithForLoop() {
        List<Vacancy> result = new ArrayList<>(source.size());
        for (VacancyDTO dto : source) {
            result.add(new Vacancy(
                    dto.getTitle(),
                    dto.getCompany(),
                    dto.getCity(),
                    dto.getSalary(),
                    dto.getRequirements(),
                    dto.getDatePosted(),
                    dto.getSource()
            ));
        }
        return result;
    }

    @Benchmark
    public List<Vacancy> mapWithStream() {
        return source.stream()
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
    }

    @Benchmark
    public List<Vacancy> mapWithParallelStream() {
        return source.parallelStream()
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
    }
}


