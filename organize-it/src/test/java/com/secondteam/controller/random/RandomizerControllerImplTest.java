package com.secondteam.controller.random;

import com.secondteam.exception.AppException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RandomizerControllerImplTest {
    private final RandomizerControllerImpl underTest = new RandomizerControllerImpl();
    private static final InputStream stdin = System.in;
    private static String args = "5\r\n-5\r\n\"expectedSize\"\r\n";
    private static InputStream is = new ByteArrayInputStream(args.getBytes());

    @BeforeAll
    static void beforeAll() {
        System.setIn(is);
    }

    @AfterAll
    static void afterAll() {
        System.setIn(stdin);
    }

    @Test
    @Order(1)
    void execute_success() throws AppException {

        int expectedSize = 5;

        var test = underTest.execute();

        assertNotNull(test);
        assertEquals(expectedSize, test.size());
    }

    @Test
    @Order(2)
    void execute_input_less_0() {
        var exception = assertThrows(AppException.class, underTest::execute);
        assertEquals("Указанное число меньше 0",exception.getMessage());
    }

    @Test
    @Order(3)
    void execute_number_format_exeption() {
        assertThrows(AppException.class, underTest::execute);
    }

}