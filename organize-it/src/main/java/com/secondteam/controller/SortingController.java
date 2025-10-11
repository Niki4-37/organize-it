package com.secondteam.controller;

import com.secondteam.entity.Entity;
import com.secondteam.utils.DelegateListener;
import com.secondteam.utils.UtilArray;

import java.util.ArrayList;

public class SortingController implements Controller {

    @Override
    public void execute(DelegateListener delegate) {
        ArrayList<Entity> array = new ArrayList<>();
        array.add(new Entity.Builder().setField1("H").setField2("W").setField3("3").build());
        array.add(new Entity.Builder().setField1("e").setField2("o").setField3("6").build());
        array.add(new Entity.Builder().setField1("l").setField2("r").setField3("7").build());
        array.add(new Entity.Builder().setField1("l").setField2("l").setField3("1").build());
        array.add(new Entity.Builder().setField1("o").setField2("d").setField3("99").build());

        UtilArray utilArray = new UtilArray(array);
        utilArray.sort();
        array = utilArray.getArray();
        delegate.executionCompleted();
    }
}
