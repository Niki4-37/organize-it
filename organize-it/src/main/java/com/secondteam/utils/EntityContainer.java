package com.secondteam.utils;

import com.secondteam.entity.Entity;

import java.util.ArrayList;

public class EntityContainer {
    private static ArrayList<Entity> array;
    private EntityContainer(){}

    public static ArrayList<Entity> getEntityArray() {
        return array;
    }

    public static void setEntityArray(ArrayList<Entity> array) {
        EntityContainer.array = array;
    }
}
