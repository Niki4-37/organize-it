package com.secondteam.utils;

import com.secondteam.person.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class UtilArray {
    private static final int PARALLEL_THRESHOLD = 1000;

    private static class MergeSortTask extends RecursiveTask<ArrayList<Person>> {
        private final ArrayList<Person> array;
        private final Comparator<Person> comparator;

        @Override
        protected ArrayList<Person> compute() {
            if (array.size() == 1) {
                return array;
            }
            int mid = array.size() / 2;
            if(array.size() < PARALLEL_THRESHOLD) {
                ArrayList<Person> left = mergeSort(new ArrayList<>(array.subList(0, mid)), comparator);
                ArrayList<Person> right = mergeSort(new ArrayList<>(array.subList(mid, array.size())), comparator);
                return merge(left, right, comparator);
            }
            MergeSortTask leftTask = new MergeSortTask(new ArrayList<>(array.subList(0, mid)), comparator);
            leftTask.fork();
            MergeSortTask rightTask = new MergeSortTask(new ArrayList<>(array.subList(mid, array.size())), comparator);
            ArrayList<Person> right = rightTask.compute();
            ArrayList<Person> left = leftTask.join();
            return merge(left, right, comparator);
        }

        private static ArrayList<Person> merge(ArrayList<Person> left, ArrayList<Person> right, Comparator<Person> comparator) {
            ArrayList<Person> result = new ArrayList<>(left.size() + right.size());
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

        public MergeSortTask(ArrayList<Person> array, Comparator<Person> comparator) {
            this.array = array;
            this.comparator = comparator;
        }
    }

    private ArrayList<Person> array;

    public void sort() {
        sort(Comparator.comparing(Person::getAge));
    }

    public void sort(Comparator<Person> comparator) {
        array = mergeSort(array, comparator);
    }

    private static ArrayList<Person> mergeSort(ArrayList<Person> array, Comparator<Person> comparator) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new MergeSortTask(array, comparator));
    }

    public UtilArray(ArrayList<Person> array) {
        this.array = array;
    }

    public ArrayList<Person> getArray() {
        return array;
    }

    public void setArray(ArrayList<Person> array) {
        this.array = array;
    }
}
