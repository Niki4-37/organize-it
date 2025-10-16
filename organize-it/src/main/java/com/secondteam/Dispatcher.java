package com.secondteam;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.secondteam.controller.Controller;
import com.secondteam.exception.AppException;
import com.secondteam.person.Person;
import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.utils.FileWriter;
import com.secondteam.utils.UtilApp;

public class Dispatcher{

    private boolean flag;

    private Map<String, Controller<Person>> controllers;
    private Map<String, Comparator<Person>> comparators;

    public Dispatcher(Map<String, Controller<Person>> controllers, Map<String, Comparator<Person>> comparators) {
        this.controllers = controllers;
        this.comparators = comparators;
    };

    public void run() {

        ConsoleHandler.write ("");
        ConsoleHandler.write ("----------------------------------------------------------------");
        ConsoleHandler.write ("");
        ConsoleHandler.write ("Приветствуем вас в приложении Organaze-it");
        ConsoleHandler.write ("");
        ConsoleHandler.write ("----------------------------------------------------------------");
        ConsoleHandler.write ("");
        
        while (true) {
            List<Person> result = getListUsingController();
            sort(result);
            writeToFile(result);
        }
    }

    private List<Person> getListUsingController() {
        
        ConsoleHandler.write (
            """
            Требуется создать коллекцию сущностей Person.
            Пожалуйста, выберите способ и введите соответствующую цифру:
              - для создания коллекции сущностей на основе файла ........ введите цифру \"1\" 
              - для создания коллекции сущностей вручную ................ введите цифру \"2\" 
              - для создания коллекции сущностей произвольным образом ... введите цифру \"3\" 
            """);
        
        String command = ConsoleHandler.read().toLowerCase();;
        
        while (!controllers.containsKey(command)) {
            ConsoleHandler.write ("Команда не найдена. Пожалуйста, повторите.");
            command = ConsoleHandler.read().toLowerCase();
        }

        List<Person> list = null;
        
        try {
            list = controllers.get(command).execute();
        } catch (AppException e) {
            ConsoleHandler.write(e.getMessage());
        }

        flag = false;
        
        return list;
    }

    private void sort(List<Person> list) {
        if (list == null || !shouldSort()) return;
        UtilApp.sort(list, getComparator());
    }

    private boolean shouldSort() { 
        String command;
        while (true) {
            ConsoleHandler.write("Требуется ли осуществить сортировку? Введите      Yes/No");
            command = ConsoleHandler.read().toLowerCase();
            if (command.equalsIgnoreCase("no" ) || command.equalsIgnoreCase("n")) return false;
            if (command.equalsIgnoreCase("yes") || command.equalsIgnoreCase("y")) return true;
            ConsoleHandler.write ("Команда не найдена. Пожалуйста, повторите.");
        }
    }

    private Comparator<Person> getComparator() {
        String command;
        while (true){
            ConsoleHandler.write (
                """
                Введите команду \"surname\" для сортировки по фамилии
                Введите команду \"name\" для сортировки по имени
                Введите команду \"age\" для сортировки по возрасту
                Введите команду \"default\" для сортировки по всем полям
                """);
            command = ConsoleHandler.read().toLowerCase();

            if (comparators.containsKey(command)) { break; }
            ConsoleHandler.write ("Команда не найдена. Пожалуйста, повторите.");
        }

        return comparators.get(command);
    }

    private void writeToFile(List<Person> list) {
        if (list == null) return;
        while (true) {
            ConsoleHandler.write("Сохранить коллекцию в файл? Введите      Yes/No");
            String command = ConsoleHandler.read().toLowerCase(); 
            if (command.equalsIgnoreCase("no" ) || command.equalsIgnoreCase("n")) return;
            if (command.equalsIgnoreCase("yes") || command.equalsIgnoreCase("y")) break;
            ConsoleHandler.write ("Команда не найдена. Пожалуйста, повторите.");
        }
        ConsoleHandler.write("Введите имя файла");
        String fileName = ConsoleHandler.read(); 
        try {
            FileWriter.write(fileName, list);
        } catch (AppException e) {
            ConsoleHandler.write(e.getMessage());
        }
    }
}
