package com.example.sortapp.strategy.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.example.sortapp.strategy.SortStrategy;

public class MergeSort<T> implements SortStrategy<T> {

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        Objects.requireNonNull(list, "List must not be null");
        Objects.requireNonNull(comparator, "Comparator must not be null");

        if (list.size() <= 1)
            return;

        List<T> sorted = mergeSort(list, comparator);
        copy(sorted, list);
    }

    private List<T> mergeSort(List<T> list, Comparator<T> comparator) {

        if (list.size() <= 1) {
            return new ArrayList<>(list);
        }

        int middle = list.size() / 2;

        List<T> left = mergeSort(new ArrayList<>(
                list.subList(0, middle)), comparator);

        List<T> right = mergeSort(new ArrayList<>(
                list.subList(middle, list.size())), comparator);

        return merge(left, right, comparator);
    }

    private List<T> merge(List<T> left, List<T> right, Comparator<T> comparator) {

        List<T> result = new ArrayList<>(left.size() + right.size());

        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {

            T leftItem = left.get(leftIndex);
            T rightItem = right.get(rightIndex);

            if (comparator.compare(leftItem, rightItem) <= 0) {
                result.add(leftItem);
                leftIndex++;
            } else {
                result.add(rightItem);
                rightIndex++;
            }
        }

        appendRemaining(result, left, leftIndex);
        appendRemaining(result, right, rightIndex);
        return result;
    }

    private void appendRemaining(List<T> target, List<T> source, int startIndex) {
        for (int i = startIndex; i < source.size(); i++)
            target.add(source.get(i));
    }

    private void copy(List<T> source, List<T> target) {
        for (int i = 0; i < source.size(); i++)
            target.set(i, source.get(i));
    }
}