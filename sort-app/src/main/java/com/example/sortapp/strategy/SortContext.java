package com.example.sortapp.strategy;

import java.util.Comparator;
import java.util.List;

public class SortContext<T> {

    private SortStrategy<T> strategy;

    public void setStrategy(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void execute(List<T> list, Comparator<T> comparator) {
        if (strategy == null) {
            throw new IllegalStateException("Strategy not set");
        }
        strategy.sort(list, comparator);
    }
}