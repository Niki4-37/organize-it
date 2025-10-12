package com.secondteam;

import java.util.Optional;

import com.secondteam.controller.Controller;
import com.secondteam.utils.ConsoleHandler;
import com.secondteam.utils.ControllerDispatcher;
import com.secondteam.utils.DelegateListener;

public class Dispatcher  implements DelegateListener{

    private Optional<Controller> controller = Optional.empty();

    public Dispatcher() {};

    public void run() {
        
        while (true) {
            ConsoleHandler.write("Поддерживает команды: \\n 'exit' - для выхода \\n выберите действие: ");
            
            var consoleString = ConsoleHandler.read();

            if (controller.isEmpty()) {
                initController(consoleString);
            }
            if (controller.isPresent()) {
                controller.get().execute(this);
            } 
        }
    }

    private void initController(String consoleString) {
        controller = ControllerDispatcher.getController(consoleString);
    }

    @Override
    public void executionCompleted() {
        controller = Optional.empty();
    }
}