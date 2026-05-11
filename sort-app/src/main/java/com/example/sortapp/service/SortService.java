package com.example.sortapp.service;

import com.example.sortapp.strategy.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SortService<T> {

    private SortStrategy<T> strategy;

    public void setStrategy(SortStrategy<T> strategy) {
        this.strategy = Objects.requireNonNull(strategy, 
            "Strategy must not be null");
    }

    public List<T> sort(List<T> list, Comparator<T> comparator) {

        Objects.requireNonNull(list, "Data must not be null");

        Objects.requireNonNull(comparator, "Comparator must not be null");

        if (strategy == null) {
            throw new IllegalStateException("Strategy is not set");
        }

        List<T> sortedList = new ArrayList<>(list);

        strategy.sort(sortedList, comparator);

        return sortedList;
    }
}
