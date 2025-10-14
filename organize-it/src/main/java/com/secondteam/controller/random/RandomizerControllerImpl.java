package com.secondteam.controller.random;

import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.controller.Controller;
import com.secondteam.exception.AppException;
import com.secondteam.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomizerControllerImpl implements Controller<Person> {
    private final EntitySynthFactory generator = new EntitySynthFactory();
    private final Random random = new Random();

    @Override
    public List<Person> execute() throws AppException {
        ConsoleHandler.write("Сколько людей вы хотите добавить? Введите положительное целое число:");
        return random.ints()
                .limit(validateInput(ConsoleHandler.read()))
                .mapToObj(i -> generator.random())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private int validateInput(String input) throws AppException {
        try {
            int value = Integer.parseInt(input);
            if (value < 0) {
                throw new AppException("Указанное число меньше 0");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new AppException(e.getMessage(), e);
        }
    }
}