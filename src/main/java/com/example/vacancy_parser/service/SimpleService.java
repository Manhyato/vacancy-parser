package com.example.vacancy_parser.service;

import org.springframework.stereotype.Service;

@Service
public class SimpleService {
    
    private volatile int counter = 0;  /* volatitle - переменная видна всем потокам сразу */
    
    public int incrementAndGet(){
        return ++counter;
    }
    
}
