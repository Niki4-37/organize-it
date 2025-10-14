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




public class FileInputControllerTest {

    private static Controller<Person> fic;
    private final static InputStream stdin = System.in;

    @BeforeAll
          static    void BeforeAll() {
        Converter <Person> converter = new PersonConverter();
        Validator validator = new PersonValidator();
        fic = new FileInputController(validator, converter);
    }

    @AfterEach
    void afterEach(){
        System.setIn(stdin);
    }


    @Test
    @DisplayName("Корректная команда \"file\"")
    void commandFileCheck() {
        System.setIn(new ByteArrayInputStream("file".getBytes()));
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
    @DisplayName("Команда \"Menu\"")
    void commandMenuCheck() {
        System.setIn(new ByteArrayInputStream("Menu".getBytes()));
        List <Person> persons = null;
        try {
            persons = fic.execute();
        } catch (AppException e) {
            e.printStackTrace();
        }
        Assertions.assertNull( persons);
    }

    @Test
    @DisplayName("Не корректный полный путь к файлу")
    void fullPathExceptionCheck()  {
        System.setIn(new ByteArrayInputStream("123".getBytes()));
        List <Person> persons = null;

        AppException exception = Assertions.assertThrows(AppException.class, () -> { fic.execute(); });
        Assertions.assertTrue(exception.getMessage().contains("Отсутствует файл c исходными данными: 123"));
    }
}
