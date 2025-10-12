package com.secondteam.controller.impl.random;

import com.secondteam.controller.Controller;
import com.secondteam.exception.AppException;
import com.secondteam.utils.DelegateListener;
import com.secondteam.utils.UtilArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class RandomizerControllerImpl implements Controller {
    private final EntitySynthFactory generator = new EntitySynthFactory();
    private final Random random = new Random();

    public RandomizerControllerImpl() {
        }

        @Override
        public void execute(DelegateListener delegate) {

            UtilArray entities = Optional.of(random.ints()
                .limit(10)
                .mapToObj(i -> generator.random())
                .toList())
                .map(ArrayList::new)
                .map(UtilArray::new)
                .get();

                delegate.executionCompleted();
            }
        }

