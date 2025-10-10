package com.secondteam.utils;

import java.util.Map;
import java.util.Optional;

import com.secondteam.controller.Controller;

public class ControllerDispatcher {

    private static Map<String, Controller> controllers;
    private ControllerDispatcher() {};

    static {
        //controllers.put("file", new FileLoaderController());
    }

    public static Optional<Controller> getController(String controllerName) {
        return controllers.containsKey(controllerName) 
        ? Optional.of(controllers.get(controllerName))
        : Optional.empty();
    }
}
