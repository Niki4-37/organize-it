package com.secondteam;

import java.util.Scanner;

import com.secondteam.controller.Controller;
import com.secondteam.exception.AppException;
import com.secondteam.utils.ControllerDispatcher;

public class ConsoleHandler {

    private Controller controller;

    public void run() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Поддерживает команды: \\n 'exit' - для выхода");
            var consoleString = scanner.nextLine();

            if ("exit".equalsIgnoreCase(consoleString)) { break; }


        }

        scanner.close();
        System.out.println("Приложение завершило работу.");
    }

    private void initController(String consoleString) {
        controller = ControllerDispatcher.getController(consoleString).orElseThrow(new AppException());
    }
}
