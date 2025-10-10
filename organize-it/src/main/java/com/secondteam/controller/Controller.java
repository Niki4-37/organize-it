package com.secondteam.controller;

import com.secondteam.exception.AppException;

import java.util.List;

//Правильно было бы возвращать Optional <List<T>>
public interface Controller<T> {

    List<T> execute () throws AppException;

}

