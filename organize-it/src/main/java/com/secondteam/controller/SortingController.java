package com.secondteam.controller;

import com.secondteam.person.Person;
import com.secondteam.utils.PersonContainer;
import com.secondteam.utils.DelegateListener;
import com.secondteam.utils.UtilArray;

import java.util.Comparator;
import java.util.Scanner;


public class SortingController implements Controller {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute(DelegateListener delegate) {
        int fieldId = requestSortFieldIdFromUser();
        Comparator<Person> comparator = getComparatorByFieldId(fieldId);

        UtilArray utilArray = new UtilArray(PersonContainer.getPersonArray());
        utilArray.sort(comparator);
        PersonContainer.setPersonArray(utilArray.getArray());

        if(delegate != null) {
            delegate.executionCompleted();
        }
    }

    private Comparator<Person> getComparatorByFieldId(int fieldId) {
        switch (fieldId) {
            case 1:
                return Comparator.comparing(Person::getFirstName);
            case 2:
                return Comparator.comparing(Person::getLastName);
            case 3:
                return Comparator.comparing(Person::getAge);
            default:
                throw new IllegalArgumentException("fieldToSortBy must be 1, 2 or 3");
        }
    }

    private int requestSortFieldIdFromUser() {
        while (true) {
            System.out.print("Enter field to sort by(1-3). Leave empty to sort by 3rd: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return 3;
            }
            if (input.matches("[1-3]")) {
                return Integer.parseInt(input);
            }
            System.out.println("Invalid input");
        }
    }
}
