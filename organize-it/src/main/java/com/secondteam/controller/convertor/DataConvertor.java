package com.secondteam.controller.convertor;

import com.secondteam.controller.convertor.validator.DataValidator;
import com.secondteam.entity.Person;
import com.secondteam.exception.AppException;

// Создание элемента для коллекции для реализаций FileInputController и ConsoleInputController
// Создание ИСКОМОГО элемента для всех реализаций Controller
public class DataConvertor {

    private final DataValidator validator = new DataValidator();

    public Person convert (String value) throws AppException {
        if  (!validator.validate(value)) throw new AppException("Ошибка валидации данных");
        String [] data = value.split(" ");

        return Person.builder().setFirstName(data[0])
                .setLastName(data[1])
                .setAge(Integer.parseInt(data[2]))
                .build();
    }
}
