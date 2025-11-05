package com.example.vacancy_parser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.vacancy_parser.util.ThreadManager;

@SpringBootApplication
public class VacancyParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacancyParserApplication.class, args);
	}

	@Bean
	public CommandLineRunner  demoRunner(ThreadManager threadManager){
		return args -> {
			threadManager.startDemo();
		};
	}
}
