package com.example.sortapp.controller;

import com.example.sortapp.domain.model.User;
import com.example.sortapp.repository.UserRepository;
import com.example.sortapp.service.SortService;
import com.example.sortapp.strategy.SortStrategy;
import com.example.sortapp.strategy.impl.BubbleSort;
import com.example.sortapp.strategy.impl.MergeSort;

import java.util.Comparator;
import java.util.List;

public class SortController {

    private final SortService<User> sortService = new SortService<>();
    private final UserRepository repository = new UserRepository();

    public void addUser(User user) {
        repository.add(user);
    }

    public void addUsers(List<User> users) {
        repository.addAll(users);
    }

    public List<User> getAllUsers() {
        return repository.getAll();
    }

    public void clearUsers() {
        repository.clear();
    }

    public int getUsersCount() {
        return repository.size();
    }

    public List<User> sort(
            List<User> list,
            int strategyChoice,
            int comparatorChoice) {

        SortStrategy<User> strategy = resolveStrategy(strategyChoice);

        Comparator<User> comparator = resolveComparator(comparatorChoice);

        sortService.setStrategy(strategy);

        return sortService.sort(list, comparator);
    }

    public List<User> loadFromFile(String path) {
        return repository.readFromFile(path);
    }

    public void saveToFile(String path, List<User> users) {
        repository.appendToFile(path, users);
    }

    private SortStrategy<User> resolveStrategy(int choice) {
        return switch (choice) {
            case 1 -> new BubbleSort<>();
            case 2 -> new MergeSort<>();
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
