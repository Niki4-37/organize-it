package com.secondteam.utils;

import com.secondteam.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import java.util.*;
import java.util.function.*;

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

    

    // Утилиты для бинарного поиска 
    public static class BinarySearchUtils {

        /**
         * Бинарный поиск в отсортированном списке с использованием callback для извлечения значения
         * 
         * @param list отсортированный список
         * @param key искомое значение
         * @param valueExtractor функция для извлечения значения из элемента списка
         * @param comparator компаратор для сравнения значений
         * @return индекс элемента или -1, если элемент не найден
         */
        public static <E, V> int binarySearch(
            List<E> list, 
            V key, 
            Function<E, V> valueExtractor, 
            Comparator<V> comparator) {
        
            validateArguments(list, key, valueExtractor, comparator);
        
            int left = 0;
            int right = list.size() - 1;
        
            while (left <= right) {
                int mid = left + (right - left) / 2;
                E midElement = list.get(mid);
                V midValue = valueExtractor.apply(midElement);
                int cmp = comparator.compare(midValue, key);
            
                if      (cmp == 0) { return  mid; } 
                else if (cmp  < 0) { left  = mid + 1; } 
                else               { right = mid - 1; }
            }
        
            return -1; 
        }

        /**
         * Бинарный поиск с возвратом первого вхождения в списке с дубликатами
         * 
         * @param list отсортированный список (может содержать дубликаты)
         * @param key искомое значение
         * @param valueExtractor функция для извлечения значения из элемента списка
         * @param comparator компаратор для сравнения значений
         * @return индекс первого вхождения или -1, если элемент не найден
         */
        public static <E, V> int binarySearchFirst(
            List<E> list, 
            V key, 
            Function<E, V> valueExtractor, 
            Comparator<V> comparator) {
        
            validateArguments(list, key, valueExtractor, comparator);
        
            int left = 0;
            int right = list.size() - 1;
            int result = -1;
        
            while (left <= right) {
                int mid = left + (right - left) / 2;
                E midElement = list.get(mid);
                V midValue = valueExtractor.apply(midElement);
                int cmp = comparator.compare(midValue, key);
            
                if (cmp == 0) {
                    result = mid; 
                    right = mid - 1; 
                } else if (cmp < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        
            return result;
        }

        /**
         * Бинарный поиск с возвратом последнего вхождения в списке с дубликатами
         * 
         * @param list отсортированный список (может содержать дубликаты)
         * @param key искомое значение
         * @param valueExtractor функция для извлечения значения из элемента списка
         * @param comparator компаратор для сравнения значений
         * @return индекс последнего вхождения или -1, если элемент не найден
         */
        public static <E, V> int binarySearchLast(
            List<E> list, 
            V key, 
            Function<E, V> valueExtractor, 
            Comparator<V> comparator) {
        
            validateArguments(list, key, valueExtractor, comparator);
        
            int left = 0;
            int right = list.size() - 1;
            int result = -1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;
                E midElement = list.get(mid);
                V midValue = valueExtractor.apply(midElement);
                int cmp = comparator.compare(midValue, key);
            
                if (cmp == 0) {
                    result = mid; 
                    left = mid + 1; 
                } else if (cmp < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        
            return result;
        }

        /**
         * Валидация аргументов
         */
        private static <E, V> void validateArguments(
            List<E> list, 
            V key, 
            Function<E, V> valueExtractor, 
            Comparator<V> comparator) {
        
            if (list           == null) { throw new IllegalArgumentException("Список не может быть null"); }
            if (key            == null) { throw new IllegalArgumentException("Ключ не может быть null"); }
            if (valueExtractor == null) { throw new IllegalArgumentException("Функция извлечения значения не может быть null"); }
            if (comparator     == null) { throw new IllegalArgumentException("Компаратор не может быть null"); }
        }

        // Перегрузки

        /**
         * Бинарный поиск для Comparable типов
         */
        public static <E, V extends Comparable<V>> int binarySearch(
            List<E> list, 
            V key, 
            Function<E, V> valueExtractor) {
        
            return binarySearch(list, key, valueExtractor, Comparator.naturalOrder());
        }

        /**
         * Бинарный поиск первого вхождения для Comparable типов
         */
        public static <E, V extends Comparable<V>> int binarySearchFirst(
            List<E> list, 
            V key, 
            Function<E, V> valueExtractor) {
        
            return binarySearchFirst(list, key, valueExtractor, Comparator.naturalOrder());
        }

        /**
         * Бинарный поиск последнего вхождения для Comparable типов
         */
        public static <E, V extends Comparable<V>> int binarySearchLast(
            List<E> list, 
            V key, 
            Function<E, V> valueExtractor) {
        
            return binarySearchLast(list, key, valueExtractor, Comparator.naturalOrder());
        }
    }
  
}
