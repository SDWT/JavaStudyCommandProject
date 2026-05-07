package com.example.sortapp.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.sortapp.domain.model.User;

public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public void add(User user) {
        users.add(user);
    }

    public void addAll(List<User> users) {
        this.users.addAll(users);
    }

    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    public void clear() {
        users.clear();
    }

    public int size() {
        return users.size();
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public List<User> readFromFile(String path) {
        // TODO
        return null;
    }

    public void appendToFile(String path, List<User> users) {
        // TODO
    }
}