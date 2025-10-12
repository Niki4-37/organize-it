package com.secondteam.controller.random;

import com.secondteam.controller.Controller;
import com.secondteam.exception.AppException;
import com.secondteam.person.Person;

import java.util.List;
import java.util.Random;

public class RandomizerControllerImpl implements Controller<Person> {
    private final EntitySynthFactory generator = new EntitySynthFactory();
    private final Random random = new Random();

    @Override
    public List<Person> execute() throws AppException {
        return random.ints()
                        .limit(10)
                        .mapToObj(i -> generator.random())
                        .toList();
    }
}

