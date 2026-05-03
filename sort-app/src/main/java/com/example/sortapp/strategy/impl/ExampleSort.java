package com.example.sortapp.strategy.impl;

import com.example.sortapp.strategy.SortStrategy;

import java.util.Comparator;
import java.util.List;

public class ExampleSort<T> implements SortStrategy<T> {

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        // реализация сортировки
    }
}