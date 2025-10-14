package com.secondteam.utils;

import com.secondteam.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class UtilApp {
    private static final int PARALLEL_THRESHOLD = 1000;

    private static class MergeSortTask<E> extends RecursiveTask<List<E>> {
        private final List<E> list;
        private final Comparator<E> comparator;

        @Override
        protected List<E> compute() {
            if (list.size() == 1) {
                return list;
            }
            int mid = list.size() / 2;
            if (list.size() < PARALLEL_THRESHOLD) {
                List<E> left = mergeSort(list.subList(0, mid), comparator);
                List<E> right = mergeSort(list.subList(mid, list.size()), comparator);
                return merge(left, right);
            }
            MergeSortTask<E> leftTask = new MergeSortTask<>(list.subList(0, mid), comparator);
            leftTask.fork();
            MergeSortTask<E> rightTask = new MergeSortTask<>(list.subList(mid, list.size()), comparator);
            List<E> right = rightTask.compute();
            List<E> left = leftTask.join();
            return merge(left, right);
        }

        private List<E> merge(List<E> left, List<E> right) {
            List<E> result = new ArrayList<>(left.size() + right.size());
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

        public MergeSortTask(List<E> list, Comparator<E> comparator) {
            this.list = list;
            this.comparator = comparator;
        }
    }

    public static <E extends Comparable<E>> void sort(List<E> list) {
        sort(list, Comparator.naturalOrder());
    }

    public static <E> void sort(List<E> list, Comparator<E> comparator) {
        List<E> sorted = mergeSort(list, comparator);
        list.clear();
        list.addAll(sorted);
    }

    public static void specialSort(List<Person> list) {
        List<Person> evenPersons = new ArrayList<>();
        List<Integer> evenPositions = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Person person = list.get(i);
            if(person.getAge() % 2 == 0) {
                evenPersons.add(person);
                evenPositions.add(i);
            }
        }
        sort(evenPersons, Comparator.comparingInt(Person::getAge));
        for (int i = 0; i < evenPositions.size(); i++) {
            list.set(evenPositions.get(i), evenPersons.get(i));
        }
    }

    private static <E> List<E> mergeSort(List<E> list, Comparator<E> comparator) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new MergeSortTask<>(list, comparator));
    }

    private UtilApp(){}
}
