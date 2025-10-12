package com.secondteam.controller;

import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.validator.Validator;
import com.secondteam.exception.AppException;

import java.util.List;

//Основа для реализаций Controller: FileInputController, ConsoleInputController
public abstract class InputController <T> implements Controller<T> {

    final Validator validator;

    final Converter<T> converter;

    InputController (Validator validator, Converter<T> converter) {
        this.validator = validator;
        this.converter = converter;
    }

    @Override
    public abstract List<T> execute () throws AppException;


    //Возврат в предыдущее меню
    final boolean backToMenu (String str) {
        boolean result = str.equalsIgnoreCase("menu");
        if (result) {
            ConsoleHandler.write ("Вы перешли в главное меню");
            return true;
        }

        return false;
    }
}
