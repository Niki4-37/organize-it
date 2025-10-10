package com.secondteam.controller;


import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.validator.Validator;
import com.secondteam.exception.AppException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileInputController <T> extends InputController<T> {

    //Путь к файлу для данных, например, для person = "src/main/resources/person_source.txt"
    //Путь к файлу для данных других Entity – сделать по аналогии
    // Файлы заполнить данными
    private final String fileNameSource;

    // путь к файлу пользователя
    private String fileName;
    public FileInputController (Validator validator, Converter<T> converter, String fileNameSource) {
        super (validator,converter);
        this.fileNameSource = fileNameSource;
    }

    @Override
    public List <T> execute() throws AppException {

        // Чтение коллекции
        ConsoleHandler.write("Введите в консоли путь к файлу или введите команду \"file\" для " +
                "использования встроенного файла с данными");
        fileName = ConsoleHandler.read();
        if (backToMenu(fileName)) return null;


        if (fileName.equalsIgnoreCase("file")) fileName = fileNameSource;
        List <String> lineList = readFile(fileName);
        result = createCollection (lineList);

        // Сортируем коллекцию
        //Раскомментировать, когда появится утилитный класс AppUtil
        //AppUtil.Sort (result);

//        writeCollectionToFile(result);

        // метод связан c бинарным поиском
//        manageBinarySearch();

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


    // Реализован проброс ошибки на верхний уровень
    private List <T>createCollection (List<String> data) throws AppException {
        List <T> result = new ArrayList<>(data.size());
        int line = 1;
        try{
            for (String value: data) {
                if (!validator.validate(value)) throw new AppException("Ошибка валидации данных");
                result.add(converter.convert(value));
                line++;
            }
        } catch (AppException e) {
            ConsoleHandler.write (line + " строка содержит неверные данные в файле: " + fileName);
            throw e;
        }
        return result;
    }
}

