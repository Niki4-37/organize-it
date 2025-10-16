package com.secondteam.person;

public class Person {
    private String lastName;
    private String firstName;
    private Integer age;

    private Person(Builder builder) {
        this.lastName = builder.lastName;
        this.firstName = builder.firstName;
        this.age = builder.age;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Integer getAge() {
        return age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String lastName;
        private String firstName;
        private Integer age;

        public Builder setLastName(String lastName) {
            if (lastName == null || lastName.trim().isEmpty()) {
                throw new IllegalArgumentException("Фамилия не может быть пустой");
            }
            this.lastName = lastName;
            return this;
        }

        public Builder setFirstName(String firstName) {
            if (firstName == null || firstName.trim().isEmpty()) {
                throw new IllegalArgumentException("Имя не может быть пустым");
            }
            this.firstName = firstName;
            return this;
        }

        public Builder setAge(String ageStr) {
            if (ageStr == null || ageStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Возраст не может быть пустым");
            }
            try {
                this.age = Integer.parseInt(ageStr);
                if (age < 0) {
                    throw new IllegalArgumentException("Возраст не может быть отрицательным");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Возраст должен быть целым числом");
            }
            return this;
        }

        public Person build() {
            if (lastName == null || firstName == null || age == null) {
                throw new IllegalStateException("Все поля должны быть заполнены");
            }
            return new Person(this);
        }
    }

    @Override
    public String toString() {
        // return "Person{" +
        //         "lastName='" + lastName + '\'' +
        //         ", firstName='" + firstName + '\'' +
        //         ", age=" + age +
        //         '}';
        return lastName + " " + firstName + " " + age; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!lastName.equals(person.lastName)) return false;
        if (!firstName.equals(person.firstName)) return false;
        return age.equals(person.age);
    }

    @Override
    public int hashCode() {
        int result = lastName.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + age.hashCode();
        return result;
    }

}