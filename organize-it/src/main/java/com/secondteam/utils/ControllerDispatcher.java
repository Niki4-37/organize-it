package com.secondteam.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.secondteam.controller.Controller;
import com.secondteam.controller.DummyController;

public class ControllerDispatcher {

    private static Map<String, Controller> controllers = new HashMap<>();
    private ControllerDispatcher() {};

    static {
        controllers.put("dummy", new DummyController());
    }

    public static Optional<Controller> getController(String controllerName) {
        return controllers.containsKey(controllerName) 
        ? Optional.of(controllers.get(controllerName))
        : Optional.empty();
    }
}
