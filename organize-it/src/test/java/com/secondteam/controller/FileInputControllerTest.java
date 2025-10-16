package com.secondteam.controller;

import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.converter.PersonConverter;
import com.secondteam.controller.validator.PersonValidator;
import com.secondteam.controller.validator.Validator;
import com.secondteam.exception.AppException;
import com.secondteam.person.Person;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileInputControllerTest {
    private static Controller<Person> fic;
    private static final InputStream stdin = System.in;
    private static String args = "file\r\nMenu\r\n123\r\n";
    private static InputStream is = new ByteArrayInputStream(args.getBytes());

    @BeforeAll
    static void beforeAll() {
        Converter <Person> converter = new PersonConverter();
        Validator validator = new PersonValidator();
        fic = new FileInputController(validator, converter);
        System.setIn(is);
    }

    @AfterAll
    static void afterAll() {
        System.setIn(stdin);
    }

    @Test
    @Order(1)
    @DisplayName("Корректная команда \"file\"")
    void commandFileCheck() {

        List <Person> persons = null;
        try {
            persons = fic.execute();
        } catch (AppException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull( persons);
        Assertions.assertEquals(25, persons.size());
    }

    @Test
    @Order(2)
    @DisplayName("Команда \"Menu\"")
    void commandMenuCheck() {
        List <Person> persons = null;
        try {
            persons = fic.execute();
        } catch (AppException e) {
            e.printStackTrace();
        }
        Assertions.assertNull( persons);
    }

    @Test
    @Order(3)
    @DisplayName("Не корректный полный путь к файлу")
    void badFullPathExceptionCheck() {

        List <Person> persons = null;

        AppException exception = Assertions.assertThrows(AppException.class, () -> { fic.execute(); });
        Assertions.assertTrue(exception.getMessage().contains("Отсутствует файл c исходными данными: 123"));
    }
}
