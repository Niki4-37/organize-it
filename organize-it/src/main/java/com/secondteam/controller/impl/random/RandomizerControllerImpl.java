package com.secondteam.controller.impl.random;

import com.secondteam.controller.Controller;
import com.secondteam.exception.AppException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomizerControllerImpl implements Controller {
    private final EntitySynthFactory;
    private final Random random = new Random();

    public RandomizerControllerImpl(Randomize<T> randomize) {
        this.generator = randomize;
    }

    @Override
    public List<T> execute() throws AppException {
        var list = new ArrayList<T>();
        for (int i = 0; i < random.nextInt(100); i++) {
            list.add(generator.random());
        }
        return list;
    }
}
