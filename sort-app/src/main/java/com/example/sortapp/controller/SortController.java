package com.example.sortapp.controller;

import com.example.sortapp.domain.model.User;
import com.example.sortapp.repository.UserRepository;
import com.example.sortapp.service.SortService;
import com.example.sortapp.strategy.SortStrategy;
import com.example.sortapp.strategy.impl.MergeSort;
import com.example.sortapp.strategy.impl.BubbleSort;

import java.util.Comparator;
import java.util.List;

public class SortController {

    private final SortService service = new SortService();
    private final UserRepository repository = new UserRepository();

    public List<User> sort(
            List<User> data,
            int strategyChoice,
            int comparatorChoice
    ) {
        SortStrategy<User> strategy = resolveStrategy(strategyChoice);
        Comparator<User> comparator = resolveComparator(comparatorChoice);

        return service.sort(data, strategy, comparator);
    }

    public List<User> loadFromFile(String path) {
        return repository.readFromFile(path);
    }

    public void saveToFile(String path, List<User> users) {
        repository.appendToFile(path, users);
    }

    private SortStrategy<User> resolveStrategy(int choice) {
        return switch (choice) {
            case 1 -> new BubbleSort<User>();
            case 2 -> new MergeSort<User>();
            default -> throw new IllegalArgumentException("Invalid strategy");
        };
    }

    private Comparator<User> resolveComparator(int choice) {
        return switch (choice) {
            case 1 -> Comparator.comparing(User::getName);
            case 2 -> Comparator.comparing(User::getEmail);
            case 3 -> Comparator.comparingInt(User::getBirthYear);
            default -> throw new IllegalArgumentException("Invalid comparator");
        };
    }
}
