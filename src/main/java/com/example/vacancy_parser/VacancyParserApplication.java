package com.example.vacancy_parser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.vacancy_parser.util.ThreadManager;
import com.example.vacancy_parser.util.ThreadStateDemo;

@SpringBootApplication
public class VacancyParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacancyParserApplication.class, args);
	}
	/*
	@Bean
	public CommandLineRunner  demoRunnerTask1(ThreadManager threadManager){
		return args -> {
			threadManager.startDemo();
		};
	}
	*/
	@Bean
	public CommandLineRunner demoRunner(ThreadStateDemo demo) {
    	return args -> {
        	demo.runDemo();
    	};
	}
}
