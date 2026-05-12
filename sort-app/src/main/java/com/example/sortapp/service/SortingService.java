package com.example.sortapp.service;

import com.example.sortapp.model.User;
import com.example.sortapp.strategy.SortStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SortingService {
    private SortStrategy<User> strategy;

    public void setStrategy(SortStrategy<User> strategy) {
        this.strategy = strategy;
    }

    public void executeSort(List<User> data, Comparator<User> comparator) {
        if (strategy == null) {
            throw new IllegalStateException("Выберите стратегию сортировки!");
        }
        strategy.sort(data, comparator);
    }

    // ДОПОЛНИТЕЛЬНОЕ ЗАДАНИЕ №4 (4 ВАРИАНТА ПОИСКА)

    // 1. По полю Name
    public void countByName(List<User> data, User target) {
        countAndPrintOccurrences(data, target, (u1, u2) ->
                u1.getName().compareTo(u2.getName()));
    }

    // 2. По полю Password
    public void countByPassword(List<User> data, User target) {
        countAndPrintOccurrences(data, target, (u1, u2) ->
                u1.getPassword().compareTo(u2.getPassword()));
    }

    // 3. По полю Email
    public void countByEmail(List<User> data, User target) {
        countAndPrintOccurrences(data, target, (u1, u2) ->
                u1.getEmail().compareTo(u2.getEmail()));
    }

    // 4. По объекту целиком
    public void countFullMatches(List<User> data, User target) {
        countAndPrintOccurrences(data, target, (u1, u2) ->
                u1.equals(u2) ? 0 : 1);
    }

    /**
     * Обертка для запуска поиска и вывода результата.
     */
    public <T> void countAndPrintOccurrences(List<T> data, T targetN, Comparator<T> comparator) {
        int result = countOccurrences(data, targetN, comparator);
        System.out.println("Результат многопоточного подсчета: найден(о) " + result + " вхождений.");
    }

    // УНИВЕРСАЛЬНЫЙ МНОГОПОТОЧНЫЙ ДВИЖОК

    private <T> int countOccurrences(List<T> data, T targetN, Comparator<T> comparator) {
        if (data == null || data.isEmpty() || comparator == null || targetN == null) {
            return 0;
        }

        int threadCount = Runtime.getRuntime().availableProcessors();
        AtomicInteger totalCount = new AtomicInteger(0);
        Thread[] threads = new Thread[threadCount];

        int size = data.size();
        int chunkSize = (int) Math.ceil((double) size / threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, size);

            if (start >= size) break;

            threads[i] = new Thread(() -> {
                int localCount = 0;
                for (int j = start; j < end; j++) {
                    // Используем переданный компаратор для сравнения
                    if (comparator.compare(data.get(j), targetN) == 0) {
                        localCount++;
                    }
                }
                totalCount.addAndGet(localCount);
            });
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                if (thread != null) thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Поток был прерван");
        }

        return totalCount.get();
    }
}
