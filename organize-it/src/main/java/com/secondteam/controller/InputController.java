package com.secondteam.controller;

import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.controller.converter.Converter;
import com.secondteam.controller.validator.Validator;
import com.secondteam.exception.AppException;

//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
import java.util.List;

//Основа для реализаций Controller: FileInputController, ConsoleInputController и RandomizeInputController !!!
public abstract class InputController <T> implements Controller<T> {

//    private final String lineSeparator = System.lineSeparator();
//    private final String fileName = "src/main/resources/result.txt";

    //Хранение коллекции - если убрать бинарный поиск - можно убрать
    List <T> result;

    // Валидация:
    //1)Валидация данных для создания искомого элемента
    // Эти данные во всех реализациях интерфейса Controller будут вводиться вручную и требуют проверки
    // 2)Валидация данных, введенных с консоли (или прочитанных строк из файла) для коллекции
    // Эти данные для реализаций FileInputController и ConsoleInputController.
    final Validator validator;


    // Конвертация данных (из строки в Entity) – после валидации.
    final Converter<T> converter;

    InputController (Validator validator, Converter<T> converter) {
        this.validator = validator;
        this.converter = converter;
    }

    @Override
    public abstract List<T> execute () throws AppException;


    //Запись коллекции в файл (дополнительное задание 2)
    // list – отсортированная коллекция;
//    final void writeCollectionToFile (List<?> list) throws AppException {
//        try (BufferedWriter bw = new BufferedWriter (new FileWriter(fileName, true))) {
//            int size = list.size();
//            for (int i = 0; i< size; i++) {
//                bw.write (list.get (i).toString());
//                bw.write (lineSeparator);
//            }
//            bw.flush();
//        }catch (IOException e) {
//            throw new AppException ("Ошибка записи коллекции в файл " + fileName, e);
//        }
//    }

    //Запись результата бинарного поиска в файл (дополнительное задание 2)
//    final void writeIndexOfSearchedElementToFile(int index) throws AppException {
//        String value;
//        try (BufferedWriter bw = new BufferedWriter (new FileWriter (fileName, true))) {
//            if (index>= 0)  {
//                value = "Индекс искомого элемента = " + index;
//                bw.write (value);
//                bw.write (lineSeparator);
//                ConsoleHandler.write (value);
//                ConsoleHandler.write ("Результат записан в файл " + fileName);
//            } else {
//                index =-1* index +1;
//                value = "Элемент на найден в коллекции. Индекс, куда бы элемент мог быть вставлен = " + index;
//                bw.write(value);
//                bw.write (lineSeparator);
//                ConsoleHandler.write (value);
//                ConsoleHandler.write ("Результат записан в файл " + fileName);
//            }
//
//            bw.flush();
//        } catch (IOException e) {
//            throw new AppException ("Ошибка записи индекса искомого элемента в файл" + fileName , e);
//        }
//    }

    //Возврат в предыдущее меню
    final boolean backToMenu (String str) {
        ConsoleHandler.write ("Вы перешли в главное меню");
        return str.equalsIgnoreCase("menu");
    }

    //Метод для работы с бинарным поиском
//    final void manageBinarySearch () throws AppException {
//        // Уточняем у пользователя необходимость произвести бинарный поиск
//        ConsoleHandler.write("Требуется ли осуществить бинарный поиск? Введите Yes/No");
//        String value;
//        do {
//            value = ConsoleHandler.read();
//            if (backToMenu(value) || value.equalsIgnoreCase("no")) return;
//        } while (!value.equalsIgnoreCase("yes"));
//
//        // ВВод данных для создания Искомого элемента
//        ConsoleHandler.write("Введите данные для создания элемента");
//        value = ConsoleHandler.read();
//        while(!validator.validate (value)) {
//            if (backToMenu(value)) return;
//            ConsoleHandler.write("Введенные данные не прошли валидацию, " +
//                    "проверьте и повторно введите данные для создания элемента");
//            value = ConsoleHandler.read();
//        }
//
//        value = ConsoleHandler.read();
//        T entity = (T) converter.convert(value);
//
//        // Осуществляем бинарный поиск и записываем результат
//        //Раскомментировать, когда появится утилитный класс AppUtil
//        //writeIndexOfSearchedElementToFile(AppUtil.binarySearch(result, entity);
//
//    }
}
