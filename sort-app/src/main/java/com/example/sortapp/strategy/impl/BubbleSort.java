package com.example.sortapp.strategy.impl;

import com.example.sortapp.strategy.SortStrategy;

import java.util.Comparator;
import java.util.List;

public class BubbleSort<T> implements SortStrategy<T> {

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        int size = list.size();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {

                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    swap(list, j, j + 1);
                }
            }
        }
    }

    private void swap(List<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
