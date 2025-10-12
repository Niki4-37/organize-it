package com.secondteam;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.secondteam.controller.FileInputController;

import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.converter.PersonConverter;
import com.secondteam.controller.random.RandomizerControllerImpl;
import com.secondteam.controller.validator.Validator;
import com.secondteam.controller.validator.PersonValidator;
import com.secondteam.person.Person;
import com.secondteam.controller.Controller;

public class Launcher {
    public static void main(String[] args) {
        Validator validator = new PersonValidator();
        Converter<Person> converter = new PersonConverter();

        Map<String, Controller<Person>> controllers = Map.of(
            "file", new FileInputController(validator, converter),
            "random", new RandomizerControllerImpl()
        );

        Comparator<Person> firstNameComparator = (one, two) -> one.getFirstName().compareToIgnoreCase(two.getFirstName());
        Comparator<Person> lastNameComparator = (one, two) -> one.getLastName().compareToIgnoreCase(two.getLastName());
        Comparator<Person> ageComparator = (one,two) -> one.getAge().compareTo(two.getAge());
        Comparator<Person> defaultComparator = firstNameComparator.thenComparing(lastNameComparator).thenComparing(ageComparator);
        
        Map<String, Comparator<Person>> comparators = Map.of(
            "field1", firstNameComparator,
            "field2", lastNameComparator,
            "field3", ageComparator,
            "default", defaultComparator
        );
        
        Dispatcher dispatcher = new Dispatcher(controllers, comparators);
        dispatcher.run();
    }
}