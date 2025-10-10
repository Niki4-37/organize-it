package com.secondteam.controller;

import com.secondteam.entity.Person;
import com.secondteam.exception.AppException;

import java.util.List;

// Вопрос Владимиру, зачем классу Dispatcher возвращаемое значение от контроллера?
// а если нужно вернуть, например, индекс искомого элемента?


// Требования к Entity:
// 3 поля - например - private String firstName, lastName; private int age;
// Должен иметь реализованные методы (equals, hash, toString, compareTo (по всем трем полям)).
// Builder на основе статического класса.
// Значение параметров:
// lastName, FirstName - только латинскими буквами (без посторонних символов типа тире и пробела),
// Начинаются с заглавной, не менее 2 символов!!!
// age - от 0 до 100

// Требования к вводимым данным:
// строка, которая после split(" ") дает массив из 3 элементов
// порядок: "firstName LastName age"


// Вместо CustomCollection – необходимо сделать утилитный класс AppUtil осуществляющий сортировку и бинарный поиск
// подобно java.util.Collections со статическими методами sort() и binarySearch().


public interface Controller {

    <T> List<T> execute () throws AppException;
    //List <?> execute () throws AppException;


}

