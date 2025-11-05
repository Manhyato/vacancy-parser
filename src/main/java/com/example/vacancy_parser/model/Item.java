package com.example.vacancy_parser.model;

public class Item {
    private final String key;
    private final String payload;

    public Item(String key, String payload) {
        this.key = key;
        this.payload = payload;
    }

    public String getKey() { return key; }
    public String getPayload() { return payload; }
}