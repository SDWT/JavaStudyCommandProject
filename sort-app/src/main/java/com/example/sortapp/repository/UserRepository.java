package com.example.sortapp.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.example.sortapp.collection.CustomList;
import com.example.sortapp.collection.CustomListAdapter;
import com.example.sortapp.domain.model.User;

public class UserRepository {

    private static final String DELIMITER = "\t";

    private final List<User> users = new CustomListAdapter<>(new CustomList<>());

    public void add(User user) {
        Objects.requireNonNull(user, "User must not be null");
        users.add(user);
    }

    public void addAll(List<User> users) {
        Objects.requireNonNull(users, "Users must not be null");
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

    public List<User> readFromFile(String filename) {
        Objects.requireNonNull(filename, "Filename must not be null");

        Path path = Path.of(filename);

        if (Files.notExists(path))
            throw new IllegalArgumentException("File does not exist: " + filename);

        try {
            return Files.lines(path)
                    .filter(line -> !line.isBlank())
                    .map(this::parseUser)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filename, e);
        }
    }

    public void appendToFile(String filename, List<User> users) {
        Objects.requireNonNull(filename, "Filename must not be null");
        Objects.requireNonNull(users, "Users must not be null");

        Path path = Path.of(filename);
        List<String> lines = users.stream()
                .map(this::toTsvString)
                .collect(Collectors.toList());

        try {
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file: " + filename, e);
        }
    }

    private User parseUser(String line) {

        String[] values = line.split(DELIMITER);
        if (values.length != 3) {
            throw new IllegalArgumentException("Invalid user format: " + line);
        }

        try {
            return new User.Builder()
                    .name(values[0])
                    .email(values[1])
                    .birthYear(Integer.parseInt(values[2]))
                    .build();

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid birth year: " + line, e);
        }
    }

    private String toTsvString(User user) {
        return String.format("%s%s%s%s%d",
                user.getName(),
                DELIMITER,
                user.getEmail(),
                DELIMITER,
                user.getBirthYear());
    }
}