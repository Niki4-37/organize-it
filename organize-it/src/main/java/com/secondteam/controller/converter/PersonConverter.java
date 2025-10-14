package com.secondteam.controller.converter;

import com.secondteam.person.Person;

public class PersonConverter implements Converter <Person> {
    @Override
    public Person convert (String value) {
        String [] data = value.split("\\s+");
        return  Person.builder().setLastName(data[0])
                .setFirstName(data[1])
                .setAge((data[2]))
                .build();
    }
}
