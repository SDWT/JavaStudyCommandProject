package com.example.sortapp.collection;

import java.util.AbstractList;

public class CustomListAdapter<T> extends AbstractList<T> {

    private final CustomList<T> customList;

    public CustomListAdapter(CustomList<T> customList) {

        this.customList = customList;
    }

    @Override
    public T get(int index) {
        return customList.get(index);
    }

    @Override
    public int size() {
        return customList.size();
    }

    @Override
    public boolean add(T element) {
        customList.add(element);
        modCount++;
        return true;
    }

    @Override
    public void add(int index, T element) {
        customList.add(index, element);
        modCount++;
    }

    @Override
    public T set(int index, T element) {
        return customList.set(index, element);
    }

    @Override
    public T remove(int index) {
        modCount++;
        return customList.remove(index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        boolean removed =
                customList.remove((T) o);
        if (removed) {
            modCount++;
        }
        return removed;
    }

    @Override
    public void clear() {
        customList.clear();
        modCount++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        return customList.contains((T) o);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int indexOf(Object o) {
        return customList.indexOf((T) o);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int lastIndexOf(Object o) {
        return customList.lastIndexOf((T) o);
    }
}