package com.secondteam.controller.random;

import com.secondteam.person.Person;

import java.util.Random;

public class EntitySynthFactory {
    private static final Random RANDOM = new Random();
    private static final String[] FIRST_NAME= {
            "Anna",
            "Inga",
            "Tom",
            "Andrey",
            "Sergey",
            "Marya",
            "Piter"
    };
    private static final String[] LAST_NAME = {
            "Williams",
            "Smith",
            "Harrison",
            "Taylor",
            "Cooper",
            "Clark",
            "Johnson"
    };

    public Person random() {
        return Person.builder()
                .setFirstName(randomName(FIRST_NAME))
                .setLastName(randomName(LAST_NAME))
                .setAge(String.valueOf(RANDOM.nextInt(0, 80)))
                .build();
    }

    private String randomName(String[] names) {
        return names[RANDOM.nextInt(names.length)];
    }
}
