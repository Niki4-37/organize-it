package com.secondteam.controller;

import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.validator.Validator;
import com.secondteam.person.Person;
import com.secondteam.exception.AppException;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileInputController extends InputController<Person> {

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

        List<Person> result;
        if (fileName.equals(fileNameSource)) result = createCollectionByInnerFile(fileName);
        //else result = createCollectionByOuterFile(fileName);
        else result = createCollectionByInnerFile(fileName);

        return result;
    }

    // Дополнительное задание 3
    private List <Person> createCollectionByOuterFile(String fileName) throws AppException {
        List <Person> result;
        try (LineNumberReader lnr = new LineNumberReader(new FileReader(fileName))) {
            result = collectByStream (lnr);
            int lineNumber = lnr.getLineNumber();
            if (lineNumber != result.size()) {
                ConsoleHandler.write (lineNumber + " строка содержит неверные данные в файле: " + fileName);
                throw new AppException("Ошибка валидации данных");
            }
        } catch (FileNotFoundException e) {
            throw new AppException ("Отсутствует файл c исходными данными: " + fileName, e);
        } catch (IOException e) {
            throw new RuntimeException("ОШИБКА ВВОДА-ВЫВОДА");
        }
        return result;
    }

    private List <Person> createCollectionByInnerFile(String fileName) throws AppException {
        List <Person> result;
        ClassLoader loader = this.getClass().getClassLoader();
        try (LineNumberReader lnr = new LineNumberReader(new InputStreamReader
                (Objects.requireNonNull(loader.getResourceAsStream(fileName))))) {
            result = collectByStream (lnr);
            int lineNumber = lnr.getLineNumber();
            if (lineNumber != result.size()) {
                ConsoleHandler.write (lineNumber + " строка содержит неверные данные в файле: " + fileName);
                throw new AppException("Ошибка валидации данных");
            }
        } catch (FileNotFoundException e) {
            throw new AppException ("Отсутствует файл c исходными данными: " + fileName, e);
        } catch (IOException e) {
            throw new RuntimeException("ОШИБКА ВВОДА-ВЫВОДА");
        }
        return result;
    }

    private List <Person> collectByStream (LineNumberReader lnr) {
        return lnr.lines()
                .map(String::trim)
                .takeWhile(validator::validate)
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}

