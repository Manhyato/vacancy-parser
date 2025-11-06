package com.example.vacancy_parser.async;

public class RequestResult {
    private final String url;
    private final int statusCode;
    private final long responseTime;

    public RequestResult(String url, int statusCode, long responseTime) {
        this.url = url;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
    }

    public String getUrl() { return url; }
    public int getStatusCode() { return statusCode; }
    public long getResponseTime() { return responseTime; }

    @Override
    public String toString() {
        return String.format("%s -> code: %d, time: %d ms", url, statusCode, responseTime);
    }
}

