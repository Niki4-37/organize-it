package com.secondteam.controller.impl.random;

import com.secondteam.entity.Person;

import java.util.Random;

public class EntitySynthFactory {
    private static final Random RANDOM = new Random();
    private static final String[] FIELD_1 = {
            "Anna",
            "Inga",
            "Tom",
            "Andrey",
            "Sergey",
            "Marya",
            "Piter"
    };
    private static final String[] FIELD_2 = {
            "Williams",
            "Smith",
            "Harrison",
            "Taylor",
            "Cooper",
            "Clark",
            "Johnson"
    };

    public Entity random() {
        return Entity.builder()
                .setField1(randomName(FIELD_1))
                .setField2(randomName(FIELD_2))
                .setField3(RANDOM.nextInt(0, 80))
                .build();
    }

    private String randomName(String[] names) {
        return names[RANDOM.nextInt(names.length)];
    }
}
