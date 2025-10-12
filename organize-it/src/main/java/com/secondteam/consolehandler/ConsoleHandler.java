package com.secondteam.consolehandler;

import java.util.Scanner;


public class ConsoleHandler {
    public static void write (String value) {
        System.out.println(value);
    }

    public static String read () {
        Scanner sc =  new Scanner (System.in);
        String str = sc.next().trim();
        if (str.equalsIgnoreCase("exit") ) {
            write ("Вы покидаете приложение. До свидания");
            System.exit (0);
        }
        return str;
    }
}


