package com.secondteam;

import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;

import com.secondteam.controller.FileInputController;

import com.secondteam.controller.ManualInputController;
import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.converter.PersonConverter;
import com.secondteam.controller.random.RandomizerControllerImpl;
import com.secondteam.controller.validator.Validator;
import com.secondteam.controller.validator.PersonValidator;
import com.secondteam.person.Person;
import com.secondteam.controller.Controller;

public class Launcher {
    public static void main(String[] args) {
        
        Validator         validator = new PersonValidator();
        Converter<Person> converter = new PersonConverter();

        Map<String, Controller<Person>> controllers = Map.of(
            "1", new FileInputController(validator, converter),
            "2", new RandomizerControllerImpl(),
            "3", new ManualInputController(validator, converter)
        );

        Comparator<Person> defaultComparator = Comparator.comparing(Person::getLastName)
            .thenComparing(Comparator.comparing(Person::getFirstName))
            .thenComparing(Comparator.comparingInt(Person::getAge));
        
        Map<String, Comparator<Person>> comparators = Map.of(
            "1", Comparator.comparing   (Person::getLastName),
            "2", Comparator.comparing   (Person::getFirstName),
            "3", Comparator.comparingInt(Person::getAge),
            "4", defaultComparator
        );

        Map<String, Function<Person, ?>> fieldGetters = new HashMap<>();
        
        {
            fieldGetters.put("1", Person::getLastName);
            fieldGetters.put("2", Person::getFirstName);
            fieldGetters.put("3", Person::getAge);
        }
        
        Dispatcher dispatcher = new Dispatcher(controllers, comparators, fieldGetters);
        dispatcher.run();
    }
}
