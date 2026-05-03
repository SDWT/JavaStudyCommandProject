package com.example.sortapp.strategy;

import java.util.Comparator;
import java.util.List;

public interface SortStrategy<T> {
    /**
     * Основной метод выполнения сортировки
     * @param data список данных
     * @param comparator правило сравнения
     */
    void sort(List<T> data, Comparator<T> comparator);
}
