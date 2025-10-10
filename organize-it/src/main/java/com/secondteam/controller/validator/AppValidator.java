package com.secondteam.controller.validator;

// Абстрактный класс делаем, так как свойство типа int должно быть у каждого Entity
// Введена проверку на int

public abstract class AppValidator {
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

