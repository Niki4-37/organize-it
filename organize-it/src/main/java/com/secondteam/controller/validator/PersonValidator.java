package com.secondteam.controller.validator;

// Реализация Validator для Person
public class PersonValidator extends AppValidator {
    public boolean validate (String value){
        if (value == null) return false;
        String[] data = value.split(" ");
        if (data.length != 3) return false;
        return checkName(data[0]) &&checkName(data[1]) &&checkAge(data[2]);
    }

// проверка String значений для класс Person
// для других Entity – свои придумайте
    private boolean checkName (String value) {
        if (value.matches("^[A-Z][a-z]+$")) return true;
        return false;
    }

    private boolean checkAge (String value) {
        if (!validateInt(value)) return false;
        int age =Integer.parseInt(value);
        if (age < 0 || age > 100) return false;
        return true;
    }
}


