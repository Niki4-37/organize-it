package com.secondteam.controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.secondteam.utils.DelegateListener;

public class DummyController implements Controller {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute(DelegateListener delegate) {
        System.out.println("Dummy coontroller done!, enter Entity number: ");
        String value = scanner.nextLine();

        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(value);
        int entityNum = 0;
        if (matcher.find()) {
            entityNum = Integer.parseInt(matcher.group());
        }
        System.out.println("Fill entities");
        for (int i = 0; i < entityNum; ++i) {
            String element = scanner.nextLine();
            System.out.println(String.format("Step: %d, you entered: %s", i, element));
        }
        
        if (delegate != null) {
            delegate.executionCompleted();
        }
    }

}
