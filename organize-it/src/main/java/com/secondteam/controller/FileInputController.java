package com.secondteam.controller;


import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.entity.Person;
import com.secondteam.exception.AppException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// Реализация Controller
public class FileInputController extends InputController {

    private final String fileName1 = "src/main/resources/source.txt";


    @Override
    public List <Person> execute() throws AppException {

        // Чтение коллекции
        ConsoleHandler.write("Введите в консоли путь к файлу или введите команду \"file\" для " +
                "использования встроенного файла с данными");
        String fileName = ConsoleHandler.read();
        if (backToMenu(fileName)) return null;

        // "resource/source.txt" - Заполнить файл данными!!!
        if (fileName.equalsIgnoreCase("file")) fileName = fileName1;
        List <String> list = readFile(fileName);
        List <Person> result = createCollection (list);

        // Сортируем коллекцию
        //Раскомментировать, когда появится утилитный класс AppUtil
        //AppUtil.Sort (result);

        writeCollectionToFile(result);

        // метод связан
        manageBinarySearch();

        return result;
    }

    // Построчное чтение файла
    private List <String> readFile (String fileName) throws AppException {
        List <String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader (new FileReader(fileName))) {
            String value;
            while ( (value =br.readLine()) != null) {
                list.add(value);
            }
            return list;
        } catch (IOException e) {
            throw new AppException ("Отсутствует файл c исходными данными: " + fileName, e);
        }
    }

    private List <Person> createCollection (List<String> data) throws AppException {
        List <Person> result = new ArrayList<>(data.size());
        for (String str: data) {
            result.add(convertor.convert(str));
        }
        return result;
    }
}

