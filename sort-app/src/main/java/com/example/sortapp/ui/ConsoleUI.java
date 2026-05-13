package com.example.sortapp.ui;

import java.util.Scanner;
import java.util.List;

import com.example.sortapp.controller.SortController;
import com.example.sortapp.domain.model.User;
import com.example.sortapp.util.UserGenerator;

public class ConsoleUI {
    private final SortController controller = new SortController();
    private final Scanner scanner = new Scanner(System.in);

    // === ГЛАВНОЕ МЕНЮ ===
    public void start() {
        while (true) {
            System.out.println("""

                    === ГЛАВНОЕ МЕНЮ ===
                    1. Работа с пользователями
                    2. Показать пользователей
                    3. Сортировать пользователей
                    0. Выход
                    """);

            switch (readInt()) {
                case 1 -> userMenu();
                case 2 -> printUsers(controller.getAllUsers());
                case 3 -> sortMenu();
                case 0 -> {
                    System.out.println("Выход из программы...");
                    return; // выход из программы
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private void userMenu() {
        System.out.println("""

                === МЕНЮ ПОЛЬЗОВАТЕЛЕЙ ===
                1. Сгенерировать пользователей
                2. Загрузка из файла
                3. Сохранение в файл
                4. Ввести одного пользователя
                5. Ввести несколько пользователей
                9. Отчистить список
                0. Назад
                """);

        switch (readInt()) {
            case 1 -> generate();
            case 2 -> load();
            case 3 -> save();
            case 4 -> manualInputSingle();
            case 5 -> manualInputMultiple();
            case 9 -> controller.clearUsers();
            case 0 -> {
                return;
            }
            default -> System.out.println("Неверный выбор!");
        }
    }

    private void sortMenu() {

        System.out.println("""
                === МЕНЮ СОРТИРОВКИ ===
                Стратегия:
                1. Сортировка пузырьком
                2. Сортировка слиянием
                3. Сортировка по чётным значениям
                """);
        int strategy = readInt();

        System.out.println("""
                Способ сравнения:
                1. Имя
                2. Электронная почта
                3. Год рождения
                4. По трём полям Имя, затем email, затем год рождения
                """);
        int comparator = readInt();

        try {
            List<User> sorted = controller
                    .sort(controller.getAllUsers(), strategy, comparator);

            System.out.println("Отсортировано:");
            printUsers(sorted);
        } catch (IllegalArgumentException e) {
            System.out.println("Введена неверная стратегия или способ сравнения.");
        }
    }

    private void manualInputSingle() {

        System.out.println("Введите имя:");
        String name = scanner.nextLine();

        System.out.println("Введите email:");
        String email = scanner.nextLine();

        System.out.println("Введите год рождения:");
        int birthYear = readInt();

        User user = new User.Builder()
                .name(name)
                .email(email)
                .birthYear(birthYear)
                .build();
        controller.addUser(user);

        System.out.println("Пользователь добавлен!");
    }

    // === ВВОД НЕСКОЛЬКИХ ПОЛЬЗОВАТЕЛЕЙ ===
    private void manualInputMultiple() {
        while (true) {
            manualInputSingle();

            System.out.println("Добавить ещё? (y/n)");

            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    private void printUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }

        int i = 1;
        for (User user : users) {
            System.out.println((i++) + ". " + user);
        }
    }

    private void load() {

        System.out.println("Путь к файлу:");

        List<User> users = controller.loadFromFile(scanner.nextLine());

        users.forEach(controller::addUser);
    }

    private void save() {

        System.out.println("Путь к файлу:");

        controller.saveToFile(
                scanner.nextLine(),
                controller.getAllUsers());
    }

    private void generate() {

        System.out.println("Введте количество пользователей для генерации:");

        List<User> users = UserGenerator.generate(readInt());

        users.forEach(controller::addUser);
    }

    private int readInt() {

        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Введите число!");
            }
        }
    }
}