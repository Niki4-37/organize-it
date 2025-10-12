package com.secondteam.controller.converter;

// Используется для:
// 1) Создание элемента для коллекции для реализаций FileInputController и ConsoleInputController
// 2) Создание ИСКОМОГО элемента для всех реализаций Controller

public interface Converter <T> {
    T convert (String value);
}

