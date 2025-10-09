package com.secondteam.entity;

import java.util.Comparator;
import java.util.Objects;

//Entity
public class Person implements Comparable<Person> {

    private String firstName;
    private String lastName;
    private int age;

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    private Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    private Person() {
    }

    @Override
    public int compareTo(Person o) {
        return Comparator.comparing(Person::getFirstName)
                .thenComparing(Person::getLastName)
                .thenComparingInt(Person::getAge)
                .compare(this, o);
    }

    public static Builder builder() {
        return new Person.Builder();
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private int age;


        public Builder setFirstName (String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName (String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setAge (int age) {
            this.age = age;
            return this;
        }

        public Person build (){
            return new Person(firstName, lastName, age);
        }
    }
}
