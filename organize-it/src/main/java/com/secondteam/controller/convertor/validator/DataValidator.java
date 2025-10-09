package com.secondteam.controller.convertor.validator;

public class DataValidator  {

    public boolean validate(String value) {
        if (value == null) return false;
        String[] data = value.split(" ");
        if (data.length != 3) return false;

        return checkName(data[0]) && checkName(data[1]) && checkAge(data[2]);
    }

    private boolean checkName (String value) {
        if (value.matches("^[A-Z][a-z]+$")) return true;
        return false;
    }

    private boolean checkAge (String value) {
        int age;
        try {
            age = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        if (age < 0 || age > 100) return false;
        return true;
    }
}

