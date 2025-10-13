package com.secondteam.controller.random;

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
    private final int size;

    public RandomizerControllerImpl() {
        this.size = random.nextInt(0, 100);
    }

    public RandomizerControllerImpl(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Count must be positive: " + size);
        }
        this.size = size;
    }


    @Override
    public List<Person> execute() throws AppException {
        return random.ints()
                .limit(size)
                .mapToObj(i -> generator.random())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

