package com.secondteam.controller;

import com.secondteam.consolehandler.ConsoleHandler;
import com.secondteam.controller.convertor.DataConvertor;
import com.secondteam.entity.Person;
import com.secondteam.exception.AppException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

//Основа для реализаций Controller: FileInputController, ConsoleInputController и RandomizeInputController !!!
public abstract class InputController implements Controller {

    private String lineSeparator = System.lineSeparator();
    private String fileName = "src/main/resources/result.txt";

    // Конвертация данных (из строки в Entity). Включает валидацию.
    // Валидация данных для создания искомого элемента
    // Эти данные во всех реализациях интерфейса Controller будут вводиться вручную
    // Валидация данных, введенных с консоли (или прочитанных строк из файла) для коллекции
    // Эти данные для реализаций FileInputController и ConsoleInputController.
    final DataConvertor convertor = new DataConvertor();

    @Override
    public abstract List <Person> execute () throws AppException;
    //public abstract List <? > execute () throws AppException;

    //Запись коллекции в файл (дополнительное задание 2)
    // list – отсортированная коллекция;
    final void writeCollectionToFile (List<?> list) throws AppException {
        try (BufferedWriter bw = new BufferedWriter (new FileWriter(fileName, true))) {
            int size = list.size();
            for (int i = 0; i< size; i++) {
                bw.write (list.get (i).toString());
                bw.write (lineSeparator);
            }
            bw.flush();
        }catch (IOException e) {
            throw new AppException ("Ошибка записи коллекции в файл " + fileName, e);
        }
    }

    //Запись результата бинарного поиска в файл (дополнительное задание 2)
    final void writeIndexOfSearchedElementToFile(int index) throws AppException {
        try (BufferedWriter bw = new BufferedWriter (new FileWriter (fileName, true))) {
            if (index>= 0) bw.write ( "Индекс искомого элемента = " + index);
            else {
                index =-1* index +1;
                bw.write ("Элемент на найден в коллекции. Индекс, куда бы элемент мог быть вставлен = " + index);
                bw.write (lineSeparator);
            }
            bw.flush();
        } catch (IOException e) {
            throw new AppException ("Ошибка записи индекса искомого элемента в файл" + fileName , e);
        }
    }

    //Возврат в предыдущее меню
    final boolean backToMenu (String str) {
        return str.equalsIgnoreCase("menu");
    }

    //
    final void manageBinarySearch () throws AppException {
        // Уточняем у пользователя необходимость произвести бинарный поиск
        ConsoleHandler.write("Требуется ли осуществить бинарный поиск? Введите Yes/No");
        String value;
        while (true) {
            value = ConsoleHandler.read();
            if (backToMenu(value) || value.equalsIgnoreCase("no")) return;
            if (value.equalsIgnoreCase("yes")) break;
        }

        // ВВод данных для создания Искомого элемента
        ConsoleHandler.write("Введите данные для создания элемента");
        value = ConsoleHandler.read();
        Person person = convertor.convert(value);

        // Осуществляем бинарный поиск
        //Раскомментировать, когда появится утилитный класс AppUtil
        //int index = AppUtil.binarySearch(result, person);

        // Записываем результат бинарного поиска
        //Раскомментировать, когда появится утилитный класс AppUtil
        //writeIndexOfSearchedElementToFile(index);
    }
}
