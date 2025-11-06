package com.example.vacancy_parser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "parserExecutor")
    public Executor parserExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);          // базовое число потоков
        executor.setMaxPoolSize(10);          // максимум потоков
        executor.setQueueCapacity(100);       // очередь задач
        executor.setThreadNamePrefix("Parser-");
        executor.initialize();
        return executor;
    }
}

