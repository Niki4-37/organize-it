package com.secondteam;

import java.util.Comparator;
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

        Comparator<Person> defaultComparator = Comparator.comparing(Person::getLastName)
            .thenComparing(Comparator.comparing(Person::getFirstName))
            .thenComparing(Comparator.comparingInt(Person::getAge));
        
        Map<String, Comparator<Person>> comparators = Map.of(
            "surname", Comparator.comparing(Person::getLastName),
            "name", Comparator.comparing(Person::getFirstName),
            "age", Comparator.comparingInt(Person::getAge),
            "default", defaultComparator
        );
        
        Dispatcher dispatcher = new Dispatcher(controllers, comparators);
        dispatcher.run();
    }
}