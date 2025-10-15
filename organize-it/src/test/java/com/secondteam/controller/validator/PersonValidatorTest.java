package com.secondteam.controller.validator;

import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PersonValidatorTest {
    private Validator validator = new PersonValidator();


    @ParameterizedTest
    @ValueSource (strings = {
            "Ivanov Ivan 40",
            "Ivanov   Ivan   0",
            "Ivanov     Ivan    100      "})

    @DisplayName("Корректная строка")
    void positiveCheck(String value) {
        Assertions.assertTrue(validator.validate(value));
    }

    @ParameterizedTest
    @ValueSource (strings = {
            "IVanov Ivan 40",
            "iVanov Ivan 40",
            "ivanov Ivan 40",
            "   Ivanov   Ivan   40",
            "Ivanov1 Ivan 40",
            "Ivanov Ivan 101",
            "Ivanov Ivan -1",
            "Ivanov Ivan 040",
            "Ivanov 40 Ivan",
            "Ivanov Ivan ",
            "Ivanov Ivan 40 40",
            "Ivanov Ivan forty",
            ""})
    @DisplayName("Некорректная строка")
    void negativeCheck(String value) {
        Assertions.assertFalse(validator.validate(value));
    }

    @Test
    @DisplayName("Проверка null")
    void nullCheck() {
        Assertions.assertFalse (validator.validate(null));
    }

}
