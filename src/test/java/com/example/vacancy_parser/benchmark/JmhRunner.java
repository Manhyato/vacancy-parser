package com.example.vacancy_parser.benchmark;

import org.openjdk.jmh.Main;

public class JmhRunner {
    public static void main(String[] args) throws Exception {
        // -f 0 выключает форки и запускает бенчмарки в том же процессе JVM,
        // что устраняет проблему с отсутствием ForkedMain на classpath.
        Main.main(new String[]{"-f", "0", "ParsingBenchmark"});
    }
}



