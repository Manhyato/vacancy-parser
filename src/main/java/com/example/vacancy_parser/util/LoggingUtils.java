package com.example.vacancy_parser.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtils {

    // Универсальный метод для потокобезопасного логирования
    public static void logInfo(Class<?> clazz, String message) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info(message);
    }

    public static void logError(Class<?> clazz, String message, Throwable throwable) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message, throwable);
    }
}

