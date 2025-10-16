package com.secondteam;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.secondteam.controller.Controller;
import com.secondteam.exception.AppException;
import com.secondteam.person.Person;
import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.utils.FileWriter;
import com.secondteam.utils.UtilApp;

public class Dispatcher{

    private Map<String, Controller<Person>> controllers;
    private Map<String, Comparator<Person>> comparators;

    private boolean isSorted = false;

    public Dispatcher(Map<String, Controller<Person>> controllers, Map<String, Comparator<Person>> comparators) {
        this.controllers = controllers;
        this.comparators = comparators;
    };

    public void run() {
        ConsoleHandler.write ("Приветствуем вас в приложении Organaze-it ");
        
        while (true) {
            List<Person> result = null;
            isSorted = false;
            var optionalController = selectController();

            if (optionalController.isEmpty()) {
                ConsoleHandler.write ("Команда не найдена. Повторите, пожалуйста.");
                continue;
            }

            try {
                result = optionalController.get().execute();
            } catch (AppException e) {
                ConsoleHandler.write(e.getMessage());
            }

            sort(result);

            writeToFile(result);

            find(result);
        }
    }

    private Optional<Controller<Person>> selectController() {
        ConsoleHandler.write (
            """
            Требуется создать коллекцию сущностей Person
            Введите команду \"file\" для создания коллекции сущностей на основе файла
            Введите команду \"manual\" для создания коллекции сущностей вручную
            Введите команду \"random\" для создания коллекции сущностей на основе случайного ввода
            """);
        
        String command = ConsoleHandler.read().toLowerCase();;
        
        return controllers.containsKey(command) ? Optional.of(controllers.get(command)) : Optional.empty(); 
    }

    private void sort(List<Person> list) {
        if (list == null || list.isEmpty() || !shouldSort()) return;
        UtilApp.sort(list, getComparator());
        isSorted = true;
    }

    private boolean shouldSort() {
        String command;
        while (true) {
            ConsoleHandler.write("Требуется ли осуществить сортировку? Введите      Yes/No");
            command = ConsoleHandler.read().toLowerCase();
            if (command.equalsIgnoreCase("no")) return false;
            if (command.equalsIgnoreCase("yes")) return true;
            ConsoleHandler.write ("Команда не найдена. Повторите, пожалуйста.");
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
            ConsoleHandler.write ("Команда не найдена. Повторите, пожалуйста.");
        }

        return comparators.get(command);
    }

    private void writeToFile(List<Person> list) {
        if (list == null || list.isEmpty()) return;
        while (true) {
            ConsoleHandler.write("Сохранить коллекцию в файл? Введите      Yes/No");
            String command = ConsoleHandler.read().toLowerCase(); 
            if (command.equalsIgnoreCase("no")) return;
            if (command.equalsIgnoreCase("yes")) break;
            ConsoleHandler.write ("Команда не найдена. Повторите, пожалуйста.");
        }
        ConsoleHandler.write("Введите имя файла");
        String fileName = ConsoleHandler.read(); //проверить на пустую строку
        try {
            FileWriter.write(fileName, list);
        } catch (AppException e) {
            ConsoleHandler.write(e.getMessage());
        }
    }

    private void find(List<Person> list) {
        if (!isSorted) return;

        ConsoleHandler.write("Хотите найти объект?      Yes/No");
        String command = ConsoleHandler.read().toLowerCase(); 
        if (command.equalsIgnoreCase("no")) return;

        var controller = controllers.get("for_binary_search_controller");
        if (controller == null) return;
        
        List<Person> listWithSearcingElement = null;
        try {
            listWithSearcingElement = controller.execute();
        } catch (AppException e) {
            e.printStackTrace();
        }

        String key = listWithSearcingElement.get(0).getLastName() + " " + listWithSearcingElement.get(0).getFirstName();
        Function<Person, String> extractor = person -> person.getLastName() + " " + person.getFirstName(); 

        int foundIndex = UtilApp.BinarySearchUtils.binarySearch(list, key, extractor);

        System.out.println(foundIndex);

    }
}