package com.example.sortapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Predicate;

public class CountService {

    private static final int THREAD_COUNT =
            Runtime.getRuntime().availableProcessors();

    public <T> int countOccurrences(
            List<T> data,
            Predicate<T> predicate
    ) {

        Objects.requireNonNull(
                data,
                "Data must not be null"
        );

        Objects.requireNonNull(
                predicate,
                "Predicate must not be null"
        );

        if (data.isEmpty()) {
            return 0;
        }

        ExecutorService executor =
                Executors.newFixedThreadPool(THREAD_COUNT);

        List<Future<Integer>> futures =
                new ArrayList<>();

        int chunkSize = (int) Math.ceil(
                (double) data.size() / THREAD_COUNT
        );

        for (int i = 0; i < THREAD_COUNT; i++) {

            int start = i * chunkSize;

            int end = Math.min(
                    start + chunkSize,
                    data.size()
            );

            if (start >= data.size()) {
                break;
            }

            Callable<Integer> task = () -> {

                int localCount = 0;

                for (int j = start; j < end; j++) {

                    if (predicate.test(data.get(j))) {
                        localCount++;
                    }
                }

                return localCount;
            };

            futures.add(executor.submit(task));
        }

        int totalCount = 0;

        try {

            for (Future<Integer> future : futures) {
                totalCount += future.get();
            }

        } catch (
                InterruptedException e
        ) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    "Thread was interrupted",
                    e
            );

        } catch (
                ExecutionException e
        ) {

            throw new RuntimeException(
                    "Error during counting",
                    e
            );

        } finally {

            executor.shutdown();
        }

        return totalCount;
    }

    public <T> void printOccurrences(
            List<T> data,
            Predicate<T> predicate
    ) {

        int result =
                countOccurrences(data, predicate);

        System.out.println(
                "Найдено вхождений: "
                        + result
        );
    }
}