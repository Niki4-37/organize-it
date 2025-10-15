package com.secondteam.controller;

import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.validator.Validator;
import com.secondteam.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManualInputController extends InputController<Person> {

    private final Integer countLimit;

    public ManualInputController(Validator validator, Converter<Person> converter) {
        this(validator, converter, null);
    }

    public ManualInputController(Validator validator, Converter<Person> converter, Integer countLimit) {
        super(validator, converter);
        this.countLimit = countLimit;
    }

    @Override
    public List<Person> execute() {
        List<Person> people = new ArrayList<>();

        int count = (countLimit == null) ? readCount() : countLimit;

        for (int i = 1; i <= count; i++) {
            promptInput(i);
            while (true) {
                String input = ConsoleHandler.read();
                input = input.trim();

                if (backToMenu(input)) return people;

                if (!validator.validate(input)) {
                    ConsoleHandler.write("Неверный формат по валидатору.");
                    continue;
                }

                try {
                    Person person = converter.convert(input);
                    people.add(person);
                    ConsoleHandler.write("Добавлен: " + person);
                    break;
                } catch (IllegalArgumentException | IllegalStateException e) {
                    ConsoleHandler.write("Ошибка: " + e.getMessage());
                }
            }
            return Optional.of(count);
        }
    }

    private int readCount() {
        ConsoleHandler.write("Сколько людей вы хотите добавить? Введите целое положительное число:");

        while (true) {
            String countInput = ConsoleHandler.read();

            if (backToMenu(countInput)) return 0;

            try {
                int count = Integer.parseInt(countInput.trim());
                if (count <= 0) {
                    ConsoleHandler.write("Введите положительное число.");
                    continue;
                }
                return count;
            } catch (NumberFormatException e) {
                ConsoleHandler.write("Ошибка: введите целое число.");
            }
        }
    }

    private void promptInput(int index) {
        ConsoleHandler.write("Введите данные для человека #" + index + " (Фамилия Имя Возраст), например: Ivanov Ivan 37");
    }
}
