package com.secondteam.controller.impl.random;

import com.secondteam.entity.Person;

import java.util.Random;

public class PersonRandomizeImpl implements Randomize<Person> {
    private static final Random RANDOM = new Random();
    private static final String[] FIRST_NAMES = {
            "Anna",
            "Inga",
            "Tom",
            "Andrey",
            "Sergey",
            "Marya",
            "Piter"
    };
    private static final String[] LAST_NAMES = {
            "Williams",
            "Smith",
            "Harrison",
            "Taylor",
            "Cooper",
            "Clark",
            "Johnson"
    };

    @Override
    public Person random() {
        return Person.builder()
                .setFirstName(randomName(FIRST_NAMES))
                .setLastName(randomName(LAST_NAMES))
                .setAge(RANDOM.nextInt(0, 80))
                .build();
    }

    private String randomName(String[] names) {
        return names[RANDOM.nextInt(names.length)];
    }
}
