package com.example.sortapp.strategy.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.example.sortapp.strategy.SortStrategy;

public class MergeSorter<T> implements SortStrategy<T> {

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        int size = list.size();
        if (size <= 1) {
            return;
        }
        var listA = list.subList(0, size / 2);
        var listB = list.subList(size / 2, size);
        sort(listA, comparator);
        sort(listB, comparator);
        list = merge(listA, listB, comparator);
    }

    private List<T> merge(List<T> listA, List<T> listB, Comparator<T> comparator) {
        int pointer = listA.size() + listB.size() - 1;
        int pointerA = listA.size() - 1;
        int pointerB = listB.size() - 1;
        var result = new ArrayList<T>(pointer + 1);
        while (pointerA >= 0 && pointerB >= 0) {
            var a = listA.get(pointerA);
            var b = listB.get(pointerB);
            if (comparator.compare(a, b) > 0) {
                result.set(pointer, a);
                pointerA--;
            } else {
                result.set(pointer, b);
                pointerB--;
            }
            pointer--;
        }
        fillRest(result, listA, pointer, pointerA);
        fillRest(result, listB, pointer, pointerB);
        return result;
    }

    private void fillRest(
            List<T> target, List<T> source,
            int targetPointer, int sourcePointer
    ) {
        while (targetPointer >= 0 && sourcePointer >= 0) {
            var item = source.get(sourcePointer);
            target.set(targetPointer, item);
            targetPointer--;
            sourcePointer--;
        }
    }
}
