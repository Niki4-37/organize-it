package person;

import com.secondteam.person.Person;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    void testBuilderCreatesPersonSuccessfully() {
        Person person = Person.builder()
                .setLastName("Ivanov")
                .setFirstName("Ivan")
                .setAge("30")
                .build();

        assertEquals("Ivanov", person.getLastName());
        assertEquals("Ivan", person.getFirstName());
        assertEquals(30, person.getAge());
    }

    @Test
    void testBuilderThrowsOnEmptyLastName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Person.builder().setLastName("").setFirstName("Ivan").setAge("30").build()
        );
        assertTrue(exception.getMessage().contains("Фамилия"));
    }

    @Test
    void testBuilderThrowsOnEmptyFirstName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Person.builder().setLastName("Ivanov").setFirstName("").setAge("30").build()
        );
        assertTrue(exception.getMessage().contains("Имя"));
    }

    @Test
    void testBuilderThrowsOnInvalidAge() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Person.builder().setLastName("Ivanov").setFirstName("Ivan").setAge("-1").build()
        );
        assertTrue(exception.getMessage().contains("отрицательным"));

        exception = assertThrows(IllegalArgumentException.class, () ->
                Person.builder().setLastName("Ivanov").setFirstName("Ivan").setAge("abc").build()
        );
        assertTrue(exception.getMessage().contains("целым числом"));
    }

    @Test
    void testEqualsAndHashCode() {
        Person p1 = Person.builder().setLastName("Ivanov").setFirstName("Ivan").setAge("30").build();
        Person p2 = Person.builder().setLastName("Ivanov").setFirstName("Ivan").setAge("30").build();
        Person p3 = Person.builder().setLastName("Petrov").setFirstName("Ivan").setAge("30").build();

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
    }
}
