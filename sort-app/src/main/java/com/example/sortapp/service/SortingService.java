package com.example.sortapp.service;

import com.example.sortapp.strategy.SortStrategy;

import java.util.Comparator;
import java.util.List;

public class SortingService<T> {
    private SortStrategy<T> strategy;

    // Метод для выбора стратегии
    public void setStrategy(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }

    // Вызов сортировки
    public void executeSort(List<T> data, Comparator<T> comparator) {
        if (strategy == null) {
            throw new IllegalStateException("Выберите стратегию сортировки!");
        }
        strategy.sort(data, comparator);
    }
}

