package com.secondteam.controller;


import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.converter.PersonConverter;
import com.secondteam.controller.validator.PersonValidator;
import com.secondteam.controller.validator.Validator;
import com.secondteam.person.Person;
import com.secondteam.exception.AppException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileInputController extends InputController<Person> {


    //"src\\main\\resources\\person_source.txt"
    //organize-it/target/target/person_source.txt
    private final String fileNameSource = "person_source.txt";


    public FileInputController (Validator validator, Converter<Person> converter) {
        super (validator,converter);
    }

    @Override
    public List <Person> execute() throws AppException {

        ConsoleHandler.write("Введите в консоли путь к файлу или введите команду \"file\" для " +
                "использования встроенного файла с данными");

        String fileName = ConsoleHandler.read();
        if (backToMenu(fileName)) return null;
        if (fileName.equalsIgnoreCase("file")) fileName = fileNameSource;

        List <String> lineList = readFile(fileName);
        List<Person> result;
        result = createCollection (lineList);

        // By Stream
        //result = createCollectionByStream(fileName);

        return result;
    }

    private List <String> readFile (String fileName) throws AppException {
        List <String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader (new FileReader(fileName))) {
            String value;
            int line = 1;
            while ( (value =br.readLine()) != null) {
                if (!validator.validate(value)) {
                    ConsoleHandler.write (line + " строка содержит неверные данные в файле: " + fileName);
                    throw new AppException("Ошибка валидации данных");
                }
                list.add(value);
                line++;
            }
            return list;
        } catch (IOException e) {
            throw new AppException ("Отсутствует файл c исходными данными: " + fileName, e);
        }
    }

    private List <Person>createCollection (List<String> data) {
        List <Person> result = new ArrayList<>(data.size());

            for (String value: data)
                result.add(converter.convert(value));

        return result;
    }

    // Дополнительное задание 3 (Не использую, показать)
    private List <Person> createCollectionByStream (String filename) {
        try {
            return Files.readAllLines(Path.of(filename)).stream()
                                    .filter(validator::validate)
                                    .map(converter::convert)
                                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        Validator validator = new PersonValidator();
        Converter <Person> converter = new PersonConverter();
        Controller <Person> controller = new FileInputController(validator, converter);

        List <Person> result = null;
        try{
            result = controller.execute();
        } catch (AppException e){
            ConsoleHandler.write(e.getMessage());
        }
        if (result == null) ConsoleHandler.write(null);
        if (result != null) ConsoleHandler.write(result.toString());
    }
}

