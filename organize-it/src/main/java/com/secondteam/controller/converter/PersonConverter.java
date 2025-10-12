package com.secondteam.controller.converter;

import com.secondteam.entity.Person;

// Реализация интерфейс Converter для Person
// Остальные реализации делайте по аналогии!!!

public class PersonConverter implements Converter <Person> {
    @Override
    public Person convert (String value) {
        String [] data = value.split("[ ]+");
        return Person.builder().setFirstName(data[0])
                .setLastName(data[1])
                .setAge(Integer.parseInt(data[2]))
                .build();
    }
}
