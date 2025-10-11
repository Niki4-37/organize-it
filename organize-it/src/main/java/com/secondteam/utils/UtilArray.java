package com.secondteam.utils;

import com.secondteam.entity.Entity;

import java.util.ArrayList;
import java.util.Comparator;

public class UtilArray {
    private ArrayList<Entity> array;

    public void sort() {
        sort(Comparator.comparing(Entity::getField3));
    }

    public void sort(Comparator<Entity> comparator) {
        array = mergeSort(array, comparator);
    }

    private ArrayList<Entity> mergeSort(ArrayList<Entity> array, Comparator<Entity> comparator) {
        if (array.size() == 1) {
            return array;
        }
        int mid = array.size() / 2;
        ArrayList<Entity> left = mergeSort(new ArrayList<>(array.subList(0, mid)), comparator);
        ArrayList<Entity> right = mergeSort(new ArrayList<>(array.subList(mid, array.size())), comparator);

        return merge(left, right, comparator);
    }

    private ArrayList<Entity> merge(ArrayList<Entity> left, ArrayList<Entity> right, Comparator<Entity> comparator) {
        ArrayList<Entity> result = new ArrayList<>(left.size() + right.size());
        int leftIndex = 0, rightIndex = 0;
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (comparator.compare(left.get(leftIndex), right.get(rightIndex)) > 0) {
                result.add(right.get(rightIndex));
                rightIndex++;
            } else {
                result.add(left.get(leftIndex));
                leftIndex++;
            }
        }
        if (leftIndex == left.size()) {
            result.addAll(right.subList(rightIndex, right.size()));
        } else {
            result.addAll(left.subList(leftIndex, left.size()));
        }
        return result;
    }


    public UtilArray(ArrayList<Entity> array) {
        this.array = array;
    }

    public ArrayList<Entity> getArray() {
        return array;
    }

    public void setArray(ArrayList<Entity> array) {
        this.array = array;
    }
}
