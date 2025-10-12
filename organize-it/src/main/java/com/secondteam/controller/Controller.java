package com.secondteam.controller;

import com.secondteam.exception.AppException;

import java.util.List;

public interface Controller<T> {

    List<T> execute () throws AppException;

}

