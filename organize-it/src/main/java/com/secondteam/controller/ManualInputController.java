package com.secondteam.controller;

import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.validator.Validator;
import com.secondteam.exception.AppException;
import com.secondteam.model.Person;

import java.util.ArrayList;
import java.util.List;

public class ManualInputController extends InputController<Person> {

    public ManualInputController(Validator validator, Converter<Person> converter) {
        super(validator, converter);
    }

    @Override
    public List<Person> execute() throws AppException {
        List<Person> people = new ArrayList<>();

        ConsoleHandler.write("Сколько людей вы хотите добавить? Введите целое число:");

        int count = 0;
        while (true) {
            String countInput = ConsoleHandler.read();

            if (backToMenu(countInput)) return people;

            try {
                count = Integer.parseInt(countInput.trim());
                if (count <= 0) {
                    ConsoleHandler.write("Введите положительное число.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                ConsoleHandler.write("Ошибка: введите целое число.");
            }
        }

        for (int i = 1; i <= count; i++) {
            ConsoleHandler.write("Введите данные для человека #" + i + " (Фамилия Имя Возраст):");

            while (true) {
                String input = ConsoleHandler.read();

                if (backToMenu(input)) return people;

                if (!validator.isValid(input)) {
                    ConsoleHandler.write("Неверный формат. Пример: Иванов Иван 37");
                    continue;
                }

                String[] parts = input.trim().split("\\s+");

                try {
                    Person person = new Person.Builder()
                            .setLastName(parts[0])
                            .setFirstName(parts[1])
                            .setAge(parts[2])
                            .build();

                    people.add(person);
                    ConsoleHandler.write("Добавлен: " + person);
                    break; // переходим к следующему человеку

                } catch (IllegalArgumentException | IllegalStateException e) {
                    ConsoleHandler.write("Ошибка: " + e.getMessage());
                }
            }
        }

        return people;
    }
}