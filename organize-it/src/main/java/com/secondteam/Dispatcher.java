package com.secondteam;

import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;

import com.secondteam.controller.Controller;
import com.secondteam.exception.AppException;
import com.secondteam.person.Person;
import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.utils.FileWriter;
import com.secondteam.utils.UtilApp;

public class Dispatcher{

    private boolean isSortedFlag;

    private Map<String, Controller<Person>   > controllers;
    private Map<String, Comparator<Person>   > comparators;
    private Map<String, Function  <Person, ?>> fieldGetters;

    public Dispatcher(Map<String, Controller<Person>   > controllers, 
                      Map<String, Comparator<Person>   > comparators, 
                      Map<String, Function  <Person, ?>> fieldGetters) {
        this.controllers  = controllers;
        this.comparators  = comparators;
        this.fieldGetters = fieldGetters;
    };

    public void run() {

        ConsoleHandler.write ("");
        ConsoleHandler.write ("----------------------------------------------------------------");
        ConsoleHandler.write ("");
        ConsoleHandler.write ("Приветствуем вас в приложении \"Organaze-it\"");
        ConsoleHandler.write ("");
        ConsoleHandler.write ("----------------------------------------------------------------");
        ConsoleHandler.write ("");
        
        while (true) {
            List<Person> result = getListUsingController();
            for (Person person : result) 
                ConsoleHandler.write(person.toString());
            sort(result);
            for (Person person : result) 
                ConsoleHandler.write(person.toString());
            innerBinarySearch(result);
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
            ConsoleHandler.write ("Команда не найдена. Пожалуйста, повторите...");
            command = ConsoleHandler.read().toLowerCase();
        }

        List<Person> list = null;
        
        try {
            list = controllers.get(command).execute();
        } catch (AppException e) {
            ConsoleHandler.write(e.getMessage());
        }

        isSortedFlag = false;
        
        return list;
    }

    private void sort(List<Person> list) {
        if (list == null || !shouldSort()) 
            return;
        UtilApp.sort(list, getComparator());
        isSortedFlag = true;
    }

    private boolean shouldSort() { 
        ConsoleHandler.write("Отсортировать коллекцию? Введите: Yes(Y)/No(N)");
    
        String command = ConsoleHandler.read().toLowerCase();
    
        while (true) {
            if (command.equalsIgnoreCase("no") || command.command.equalsIgnoreCase("n")) {
                return false;
            }
            if (command.equalsIgnoreCase("yes") || command.equalsIgnoreCase("y")) {
                return true;
            }
            ConsoleHandler.write("Команда не найдена. Пожалуйста, повторите...");
            command = ConsoleHandler.read().toLowerCase();
        }
    }

    private Comparator<Person> getComparator() {

        ConsoleHandler.write (
            """
            Пожалуйста, выберите по какому полю отсортировать коллекцию и введите соответствующую цифру:
              - для сортировки по фамилии ...... введите цифру \"1\" 
              - для сортировки по имени ........ введите цифру \"2\" 
              - для сортировки по возрасту ..... введите цифру \"3\" 
              - для сортировки по всем полям ... введите цифру \"4\" 
            """);
        
        String command = ConsoleHandler.read().toLowerCase();
        
        while (!comparators.containsKey(command)) {
            ConsoleHandler.write ("Команда не найдена. Пожалуйста, повторите...");
            command = ConsoleHandler.read().toLowerCase();
        }

        return comparators.get(command);
    }

    private void innerBinarySearch(List<Person> list) {

        ConsoleHandler.write (
            """
            Пожалуйста, выберите по какому полю поизвести поиск и введите соответствующую цифру:
              - для сортировки по фамилии ...... введите цифру \"1\" 
              - для сортировки по имени ........ введите цифру \"2\" 
              - для сортировки по возрасту ..... введите цифру \"3\" 
            """);
        
        String command = ConsoleHandler.read().toLowerCase();
        
        while (!fieldGetters.containsKey(command)) {
            ConsoleHandler.write ("Команда не найдена. Пожалуйста, повторите...");
            command = ConsoleHandler.read().toLowerCase();
        }
        
        ConsoleHandler.write ("Введите значение для поиска...");
        String inputValue = ConsoleHandler.read().toLowerCase();

        int index = -1;
        
        if (isSortedFlag) {
            if (command == "1") {
                Function<Person, String> lastNameExtractor = (Function<Person, String>) fieldGetters.get(command);
                index = UtilApp.BinarySearchUtils.binarySearch(list, inputValue, lastNameExtractor, String::compareTo);
            } 
            if (command == "2") {
                Function<Person, String> firstNameExtractor = (Function<Person, String>) fieldGetters.get(command);
                index = UtilApp.BinarySearchUtils.binarySearch(list, inputValue, firstNameExtractor, String::compareTo);
            } 
            if (command == "3") {
                int ageValue = Integer.parseInt(inputValue);
                Function<Person, Integer> ageExtractor = (Function<Person, Integer>) fieldGetters.get(command);
                index = UtilApp.BinarySearchUtils.binarySearch(list, ageValue, ageExtractor, Comparator.naturalOrder());
            } 
            ConsoleHandler.write ("Индекс элемента коллекции: " + String.valueOf(index));
        } else {
            ConsoleHandler.write ("Внимание: коллекция неотсортирована.");
            while (true) {
                ConsoleHandler.write ("Отсортировать перед поиском? Введите: Yes(Y)/No(N)");
                command = ConsoleHandler.read().toLowerCase();
                if (command.equalsIgnoreCase("no" ) || command.equalsIgnoreCase("n")) break;
                if (command.equalsIgnoreCase("yes") || command.equalsIgnoreCase("y")) {
                    sort(list);
                    if (command == "1") {
                        Function<Person, String> lastNameExtractor = (Function<Person, String>) fieldGetters.get(command);
                        index = UtilApp.BinarySearchUtils.binarySearch(list, inputValue, lastNameExtractor, String::compareTo);
                    } 
                    if (command == "2") {
                        Function<Person, String> firstNameExtractor = (Function<Person, String>) fieldGetters.get(command);
                        index = UtilApp.BinarySearchUtils.binarySearch(list, inputValue, firstNameExtractor, String::compareTo);
                    } 
                    if (command == "3") {
                        int ageValue = Integer.parseInt(inputValue);
                        Function<Person, Integer> ageExtractor = (Function<Person, Integer>) fieldGetters.get(command);
                        index = UtilApp.BinarySearchUtils.binarySearch(list, ageValue, ageExtractor, Comparator.naturalOrder());
                    } 
                    ConsoleHandler.write ("Индекс элемента коллекции: " + String.valueOf(index));
                    break;
                } 
            }
        
        }
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
