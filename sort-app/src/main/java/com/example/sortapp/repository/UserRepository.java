package com.example.sortapp.repository;

import com.example.sortapp.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public void appendToFile(String path, List<User> users) {
        Path filePath = Paths.get(path);
        try {
            // Если файл не существует, создаем его с заголовками
            if (Files.notExists(filePath)) {
                String header = "Name\tEmail\tBirthYear";
                Files.writeString(filePath, header + System.lineSeparator());
            }

            // Подготавливаем строки пользователей
            List<String> lines = users.stream()
                    .map(user -> String.format("%s\t%s\t%d",
                            user.getName(),
                            user.getEmail(),
                            user.getBirthYear()))
                    .collect(Collectors.toList());

            // Дописываем данные в конец файла
            Files.write(
                    filePath,
                    lines,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}