package com.secondteam.controller;

import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.converter.PersonConverter;
import com.secondteam.controller.validator.PersonValidator;
import com.secondteam.controller.validator.Validator;
import com.secondteam.exception.AppException;
import com.secondteam.person.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManualInputControllerTest {

    private final InputStream systemIn = System.in;

    @BeforeEach
    void setUp() {
        // ничего пока не делаем здесь, будем подставлять ввод в каждом тесте
    }

    @AfterEach
    void tearDown() {
        System.setIn(systemIn);  // возвращаем System.in обратно после теста
    }

    @Test
    void testExecuteWithTwoValidInputs() throws AppException {
        String input = "2\n"               // сколько людей
                + "Ivanov Ivan 37\n"  // человек #1
                + "Petrov Petr 45\n"; // человек #2

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Validator validator = new PersonValidator();
        Converter<Person> converter = new PersonConverter();

        ManualInputController controller = new ManualInputController(validator, converter);
        List<Person> result = controller.execute();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Ivanov", result.get(0).getLastName());
        assertEquals("Ivan", result.get(0).getFirstName());
        assertEquals(37, result.get(0).getAge());

        assertEquals("Petrov", result.get(1).getLastName());
        assertEquals("Petr", result.get(1).getFirstName());
        assertEquals(45, result.get(1).getAge());
    }
}