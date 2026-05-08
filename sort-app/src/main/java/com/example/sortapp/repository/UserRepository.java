package com.example.sortapp.repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

// TODO
// Переименовать класс в соответсвии с класом модели
public class UserRepository {

    public List<Object> readFromFile(String path) {
        // TODO
        return null;
    }

    public void appendToFile(String path, List<Object> students) {
        try {
            List<String> lines = students.stream()
                    .map(obj -> {
                        Student s = (Student) obj;

                        String booksInfo = s.getBooks().stream()
                                .map(b -> b.getTitle() + ":" + b.getYear() + ":" + b.getPages())
                                .collect(Collectors.joining(";"));


                        return s.getName() + ";" + booksInfo;
                    })
                    .collect(Collectors.toList());

            Files.write(
                    Paths.get(path),
                    lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}