package com.secondteam.controller.validator;

public class PersonValidator extends AppValidator {
    @Override
    public boolean validate (String value){
        if (value == null) return false;
        String[] data = value.split("\\s+");
        if (data.length != 3) return false;
        return checkName(data[0]) &&checkName(data[1]) &&checkAge(data[2]);
    }

    private boolean checkName (String value) {

        return value.matches("^[A-Z][a-z]+$");
    }

    private boolean checkAge (String value) {
        if (!validateInt(value)) return false;
        int age =Integer.parseInt(value);
        return age >= 0 && age <= 100;
    }
}


