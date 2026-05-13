package com.example.sortapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Predicate;

public class CountService {

    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public <T> int countOccurrences(List<T> list, Predicate<T> predicate) {

        Objects.requireNonNull(list, "Список не должен быть null");

        Objects.requireNonNull(predicate, "Предикат не должен быть null");

        if (list.isEmpty())
            return 0;

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        List<Future<Integer>> futures = new ArrayList<>();

        int chunkSize = (int) Math.ceil((double) list.size() / THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {

            int start = i * chunkSize;

            int end = Math.min(start + chunkSize, list.size());

            if (start >= list.size())
                break;

            Callable<Integer> task = () -> {

                int localCount = 0;

                for (int j = start; j < end; j++)
                    if (predicate.test(list.get(j)))
                        localCount++;

                return localCount;
            };

            futures.add(executor.submit(task));
        }

        int totalCount = 0;

        try {
            for (Future<Integer> future : futures)
                totalCount += future.get();
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException("Поток был прерван", e);

        } catch (ExecutionException e) {

            throw new RuntimeException("Ошибка во время подсчёта", e);

        } finally {
            executor.shutdown();
        }

        return totalCount;
    }
}