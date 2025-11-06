package com.example.vacancy_parser.asyncfetch;

public class FinalResult {
    private final String id;
    private final String name;
    private final double price;
    private final double rate;

    public FinalResult(String id, String name, double price, double rate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rate = rate;
    }

    public  String getId() {
        return id;
    }

    public  String getName() {
        return name;
    }

    public  double getPrice() {
        return price;
    }

    public  double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "FinalResult{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", rate=" + rate +
                '}';
    }
}
