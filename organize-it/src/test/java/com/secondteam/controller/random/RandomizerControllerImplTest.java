package com.secondteam.controller.random;

import com.secondteam.exception.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class RandomizerControllerImplTest {
    private final RandomizerControllerImpl underTest = new RandomizerControllerImpl();

    @BeforeEach
    void cleanUpInput() {
        System.setIn(null);
    }

    @Test
    void execute_success() throws AppException {
        //Given
        int expectedSize = 5;
        System.setIn(new ByteArrayInputStream(String.valueOf(expectedSize).getBytes()));
        //When
        var test = underTest.execute();
        //Then
        assertNotNull(test);
        assertEquals(expectedSize, test.size());
    }

    @Test
    void execute_input_less_0() {
        //Given
        int expectedSize = -5;
        System.setIn(new ByteArrayInputStream(String.valueOf(expectedSize).getBytes()));
        //When Then
        var exception = assertThrows(AppException.class, underTest::execute);
        assertEquals("Указанное число меньше 0",exception.getMessage());
    }

    @Test
    void execute_number_format_exeption() {
        //Given
        System.setIn(new ByteArrayInputStream("expectedSize".getBytes()));
        //When Then
        assertThrows(AppException.class, underTest::execute);
    }

}