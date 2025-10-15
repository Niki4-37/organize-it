package com.secondteam.controller.validator;

public abstract class AppValidator implements Validator {
    @Override
    public abstract boolean validate (String value);

    final boolean validateInt (String value) {
        try {
            if (value.matches("^0\\d+")) return false;
            Integer.parseInt (value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

