package com.example.sortapp.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CustomList<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public CustomList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public CustomList(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Вместимость должна быть больше 0");
        elements = new Object[capacity];
    }

    public void add(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    public void add(int index, T element) {

        checkIndexForAdd(index);

        ensureCapacity();

        for (int i = size; i > index; i--)
            elements[i] = elements[i - 1];

        elements[index] = element;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        checkIndex(index);
        T oldValue = (T) elements[index];
        elements[index] = element;
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);
        T removedElement = (T) elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null;
        size--;
        return removedElement;
    }

    public boolean remove(T element) {
        int index = indexOf(element);
        if (index == -1)
            return false;
        remove(index);
        return true;
    }

    public int indexOf(T element) {
        for (int i = 0; i < size; i++)
            if (Objects.equals(elements[i], element))
                return i;
        return -1;
    }

    public int lastIndexOf(T element) {
        for (int i = size - 1; i >= 0; i--)
            if (Objects.equals(elements[i], element))
                return i;
        return -1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    private void ensureCapacity() {
        if (size < elements.length)
            return;

        int newCapacity = Math.max(1, elements.length * 2);
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(elements, 0, newArray, 0, elements.length);
        elements = newArray;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Неверный индекс");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Invalid index: " + index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Больше никаких элементов");
                }
                return (T) elements[currentIndex++];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    public boolean contains(T element) {
        return indexOf(element) != -1;
    }
}