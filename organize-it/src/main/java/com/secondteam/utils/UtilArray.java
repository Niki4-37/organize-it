package com.secondteam.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class UtilArray<E> {
    private static final int PARALLEL_THRESHOLD = 1000;

    private class MergeSortTask extends RecursiveTask<ArrayList<E>> {
        private final ArrayList<E> array;
        private final Comparator<E> comparator;

        @Override
        protected ArrayList<E> compute() {
            if (array.size() == 1) {
                return array;
            }
            int mid = array.size() / 2;
            if(array.size() < PARALLEL_THRESHOLD) {
                ArrayList<E> left = mergeSort(new ArrayList<>(array.subList(0, mid)), comparator);
                ArrayList<E> right = mergeSort(new ArrayList<>(array.subList(mid, array.size())), comparator);
                return merge(left, right, comparator);
            }
            MergeSortTask leftTask = new MergeSortTask(new ArrayList<>(array.subList(0, mid)), comparator);
            leftTask.fork();
            MergeSortTask rightTask = new MergeSortTask(new ArrayList<>(array.subList(mid, array.size())), comparator);
            ArrayList<E> right = rightTask.compute();
            ArrayList<E> left = leftTask.join();
            return merge(left, right, comparator);
        }

        private ArrayList<E> merge(ArrayList<E> left, ArrayList<E> right, Comparator<E> comparator) {
            ArrayList<E> result = new ArrayList<>(left.size() + right.size());
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

        public MergeSortTask(ArrayList<E> array, Comparator<E> comparator) {
            this.array = array;
            this.comparator = comparator;
        }
    }

    private ArrayList<E> array;


    public void sort(Comparator<E> comparator) {
        array = mergeSort(array, comparator);
    }

    private ArrayList<E> mergeSort(ArrayList<E> array, Comparator<E> comparator) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new MergeSortTask(array, comparator));
    }

    public UtilArray(ArrayList<E> array) {
        this.array = array;
    }

    public ArrayList<E> getArray() {
        return array;
    }

    public void setArray(ArrayList<E> array) {
        this.array = array;
    }
}
