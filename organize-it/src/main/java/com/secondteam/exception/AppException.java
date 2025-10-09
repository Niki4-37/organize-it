package com.secondteam.exception;

// Основная ошибка приложения
// В нее оборачиваем прочие
public class AppException extends Exception {

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}

