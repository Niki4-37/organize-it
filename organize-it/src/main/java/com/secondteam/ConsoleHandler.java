package com.secondteam;

import java.util.Optional;
import java.util.Scanner;

import com.secondteam.controller.Controller;
import com.secondteam.utils.ControllerDispatcher;
import com.secondteam.utils.DelegateListener;

public class ConsoleHandler implements DelegateListener{

    private Optional<Controller> controller = Optional.empty();

    public void run() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Поддерживает команды: \\n 'exit' - для выхода \\n выберите действие: ");
            
            var consoleString = scanner.nextLine();

            if ("exit".equalsIgnoreCase(consoleString)) { break; }

            if (controller.isEmpty()) {
                initController(consoleString);
            }
            if (controller.isPresent()) {
                controller.get().execute(this);
            } 
        }

        scanner.close();
        System.out.println("Приложение завершило работу.");
    }

    private void initController(String consoleString) {
        controller = ControllerDispatcher.getController(consoleString);
    }

    @Override
    public void executionCompleted() {
        controller = Optional.empty();
    }
}
