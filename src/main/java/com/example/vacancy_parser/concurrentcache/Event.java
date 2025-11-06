package com.example.vacancy_parser.concurrentcache;

public class Event {
    private final String type;
    private final String message;
    private final long timestamp = System.currentTimeMillis();

    public Event(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() { return type; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[" + type + "] " + message + " (" + timestamp + ")";
    }
}

