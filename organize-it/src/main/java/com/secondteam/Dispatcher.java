package com.secondteam;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.secondteam.controller.Controller;
import com.secondteam.exception.AppException;
import com.secondteam.person.Person;
import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.utils.UtilApp;

public class Dispatcher{

    private Map<String, Controller<Person>> controllers;
    private Map<String, Comparator<Person>> comparators;

    public Dispatcher(Map<String, Controller<Person>> controllers, Map<String, Comparator<Person>> comparators) {
        this.controllers = controllers;
        this.comparators = comparators;
    };

    public void run() {
        ConsoleHandler.write ("Приветствуем вас в приложении Organaze-it ");
        String command;

        while (true) {
            ConsoleHandler.write ("Требуется создать коллекцию сущностей Person");
		    ConsoleHandler.write ("Введите команду \"file\" для создания коллекции сущностей на основе файла");
		    ConsoleHandler.write ("Введите команду \"manual\" для создания коллекции сущностей вручную");
		    ConsoleHandler.write ("Введите команду \"random\" для создания коллекции сущностей на основе случайного ввода");
		    command = ConsoleHandler.read().toLowerCase();
            
            while (!controllers.containsKey(command)) {
                ConsoleHandler.write ("Команда не найдена. Повторите, пожалуйста.");
                command = ConsoleHandler.read().toLowerCase();
            }

            List<Person> result = null;
            try {
                result = controllers.get(command).execute();
                if (result == null) continue;
            } catch (AppException e) {
                ConsoleHandler.write (e.getMessage());
                continue;
            }

            sort(result);

            result.forEach(System.out::println);
        }
    }

    private void sort(List<Person> list) {
        if (!shouldSort()) return;
        var comparator = getComparator();
        UtilApp.sort(list, comparator);
    }

    private boolean shouldSort() {
        String command;
        while (true) {
            ConsoleHandler.write("Требуется ли осуществить сортировку? Введите      Yes/No");
            command = ConsoleHandler.read().toLowerCase();
            if (command.equalsIgnoreCase("no")) return false;
            if (command.equalsIgnoreCase("yes")) return true;
        }
    }

    private Comparator<Person> getComparator() {
        String command;
        while (true){
            ConsoleHandler.write ("Введите команду \"field1\" для сортировки по первому полю");
		    ConsoleHandler.write ("Введите команду \"field2\" для сортировки по второму полю");
		    ConsoleHandler.write ("Введите команду \"field3\" для сортировки по третьему полю");
            ConsoleHandler.write ("Введите команду \"default\" для сортировки по всем полям");
            command = ConsoleHandler.read().toLowerCase();

            if (!comparators.containsKey(command)) {
                ConsoleHandler.write ("Команда не найдена. Повторите, пожалуйста.");
                command = ConsoleHandler.read().toLowerCase();
            } else { break; }
        }

        return comparators.get(command);
    }

}