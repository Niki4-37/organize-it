package com.secondteam.controller.random;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;


class EntitySynthFactoryTest {
    private final EntitySynthFactory underTest = new EntitySynthFactory();

    @Test
    void random_success() {
        // When
        var person = underTest.random();
        // Then
        assertNotNull(person);
        assertTrue(StringUtils.isNotBlank(person.getFirstName()));
        assertTrue(StringUtils.isNotBlank(person.getLastName()));
        assertTrue(person.getAge() != null && person.getAge() >= 0 && person.getAge() < 80);
    }
}