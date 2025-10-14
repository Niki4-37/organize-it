package com.secondteam.controller.validator;

public abstract class AppValidator implements Validator {
    @Override
    public abstract boolean validate (String value);

    final boolean validateInt (String value) {
        try {
            Integer.parseInt (value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

