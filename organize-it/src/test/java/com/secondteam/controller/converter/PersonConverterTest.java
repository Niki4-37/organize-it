package com.secondteam.controller.converter;

import com.secondteam.person.Person;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersonConverterTest {

    @Test
    @DisplayName("Проверка соответствия")
    void equalsPerson () {
        final String value = "Ivanov Ivan 40";
        Person expectedPerson = Person.builder().setLastName("Ivanov").setFirstName("Ivan").setAge("40").build();
        Converter<Person> converter = new PersonConverter();
        Person actualPerson = converter.convert(value);
        Assertions.assertNotSame(expectedPerson, actualPerson);
        Assertions.assertEquals(expectedPerson, actualPerson);
    }
}
