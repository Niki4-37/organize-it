package com.secondteam.controller;

import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.validator.Validator;
import com.secondteam.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManualInputController extends InputController<Person> {

    public ManualInputController(Validator validator, Converter<Person> converter) {
        super(validator, converter);
    }

    @Override
    public List<Person> execute() {
        List<Person> people = new ArrayList<>();

        ConsoleHandler.write("Сколько людей вы хотите добавить? Введите целое число:");
        Optional<Integer> optionalCount = readCount();
        if (optionalCount.isEmpty()) return people;
        int count = optionalCount.get();

        for (int i = 1; i <= count; i++) {
            ConsoleHandler.write("Введите данные для человека #" + i + " (Фамилия Имя Возраст) на английском, например: Ivanov Ivan 37:");
            Optional<Person> person = readPerson();
            if (person.isEmpty()) return people;
            people.add(person.get());
            ConsoleHandler.write("Добавлен: " + person);
        }
        return people;
    }

    private Optional<Person> readPerson() {
        while (true) {
            String input = ConsoleHandler.read();
            if (backToMenu(input)) return Optional.empty();
            if (!validator.validate(input)) {
                ConsoleHandler.write("Неверный формат или символы не на английском. Пример правильного ввода: Ivanov Ivan 37");
                continue;
            }
            try {
                return Optional.of(converter.convert(input));
            } catch (IllegalArgumentException | IllegalStateException e) {
                ConsoleHandler.write("Ошибка: " + e.getMessage());
            }
        }
    }


    private Optional<Integer> readCount() {
        int count;
        while (true) {
            String countInput = ConsoleHandler.read();
            if (backToMenu(countInput)) return Optional.empty();
            try {
                count = Integer.parseInt(countInput.trim());
            } catch (NumberFormatException e) {
                ConsoleHandler.write("Ошибка: введите целое число.");
                continue;
            }
            if (count <= 0) {
                ConsoleHandler.write("Введите положительное число.");
                continue;
            }
            return Optional.of(count);
        }
    }
}