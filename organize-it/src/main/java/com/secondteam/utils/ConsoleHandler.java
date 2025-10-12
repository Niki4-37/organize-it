package com.secondteam.utils;

import java.util.Scanner;

public class ConsoleHandler{

    private static final Scanner scanner = new Scanner(System.in);

    public static void write(String message) {
        System.out.println(message);
    }

    public static String read() {
        String consoleString = scanner.next().trim();
        if ("exit".equalsIgnoreCase(consoleString)) { 
            write("Приложение завершило работу");
            System.exit(0);
        }
        return consoleString;
    }
}
