package com.secondteam.dispatcher;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.secondteam.Dispatcher;
import com.secondteam.controller.Controller;
import com.secondteam.controller.FileInputController;
import com.secondteam.controller.ManualInputController;
import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.converter.PersonConverter;
import com.secondteam.controller.random.RandomizerControllerImpl;
import com.secondteam.controller.validator.PersonValidator;
import com.secondteam.controller.validator.Validator;
import com.secondteam.person.Person;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DispatcherTest {
    private static Dispatcher dispatcher;
    private static String args = "manual\r\nrandom\r\nfile\r\n123\r\n";
    private static InputStream is = new ByteArrayInputStream(args.getBytes());

    @BeforeAll
    static void beforeAll() {
        Validator validator = new PersonValidator();
        Converter<Person> converter = new PersonConverter();

        Map<String, Controller<Person>> controllers = Map.of(
            "file", new FileInputController(validator, converter),
            "random", new RandomizerControllerImpl(),
            "manual", new ManualInputController(validator, converter)
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
        
        dispatcher = new Dispatcher(controllers, comparators);
        
        System.setIn(is);
    }

    @Test
    @Order(1)
    void testSelectManualControllerSuccess() throws Exception {
        Method getManualController = Dispatcher.class.getDeclaredMethod("selectController");
        getManualController.setAccessible(true);
        var result = (Optional) getManualController.invoke(dispatcher);

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof ManualInputController);
    }

    @Test
    @Order(2)
    void testSelectRandomizeControllerSuccess() throws Exception {
        Method getRandomizeController = Dispatcher.class.getDeclaredMethod("selectController");
        getRandomizeController.setAccessible(true);
        var result = (Optional) getRandomizeController.invoke(dispatcher);

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof RandomizerControllerImpl);
    }

    @Test
    @Order(3)
    void testSelectFileInputControllerSuccess() throws Exception {
        Method getFileInputController = Dispatcher.class.getDeclaredMethod("selectController");
        getFileInputController.setAccessible(true);
        var result = (Optional) getFileInputController.invoke(dispatcher);

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof FileInputController);
    }

    
    @Test
    @Order(4)
    void testWrongInputSelectController() throws Exception {
        Method getFileInputController = Dispatcher.class.getDeclaredMethod("selectController");
        getFileInputController.setAccessible(true);
        var result = (Optional) getFileInputController.invoke(dispatcher);

        assertTrue(result.isEmpty());
    }

    @AfterAll
    static void afterAll() {
        System.setIn(System.in);
    }
}
