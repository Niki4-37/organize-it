package com.secondteam.utils;

import com.secondteam.person.Person;

import java.util.ArrayList;

public class PersonContainer {
    private static ArrayList<Person> array;
    private PersonContainer(){}

    public static ArrayList<Person> getPersonArray() {
        return array;
    }

    public static void setPersonArray(ArrayList<Person> array) {
        PersonContainer.array = array;
    }
}
