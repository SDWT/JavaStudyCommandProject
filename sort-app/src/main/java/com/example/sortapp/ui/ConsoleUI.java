package com.example.sortapp.ui;

import java.util.Scanner;
import java.util.function.Function;
import java.util.List;

import com.example.sortapp.controller.SortController;
import com.example.sortapp.domain.model.User;
import com.example.sortapp.service.CountService;
import com.example.sortapp.util.UserGenerator;
import com.example.sortapp.validation.UserValidator;

public class ConsoleUI {

    private final CountService countService = new CountService();
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
                    4. Многопоточный подсчёт
                    0. Выход
                    """);

            switch (readInt()) {
                case 1 -> userMenu();
                case 2 -> printUsers(controller.getAllUsers());
                case 3 -> sortMenu();
                case 4 -> counting();
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
        String name = readStringProperty(UserValidator::validateAndNormalizeName);

        System.out.println("Введите email:");
        String email = readStringProperty(UserValidator::validateAndNormalizeEmail);

        System.out.println("Введите год рождения:");
        int birthYear = readInt(UserValidator::validateBirthYear);

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

    private int readInt(Function<Integer, Integer> validateMethod) {

        while (true) {
            try {
                return validateMethod.apply(readInt());
            } catch (Exception e) {
                System.out.println("Не прошло валидацию, введите другое значение! " + e.getMessage());
            }
        }
    }

    private String readStringProperty(Function<String, String> validateMethod) {

        while (true) {
            try {
                return validateMethod.apply(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Не прошло валидацию, введите другое значение! " + e.getMessage());
            }
        }
    }

    private void counting() {
        int counterMethod;
        boolean isCycle = true;
        while (isCycle) {
            System.out.println("""
                    === МЕНЮ ПОДСЧЁТА ===
                    Поле сравнения:
                    1. Имя
                    2. Электронная почта
                    3. Год рождения
                    4. По трём полям
                    """);
            counterMethod = readInt();
            switch (counterMethod) {
                case 1:
                    countingName();
                    return;
                case 2:
                    countingEmail();
                    return;
                case 3:
                    countingBirthYear();
                    return;
                case 4:
                    countingUser();
                    return;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private void countingName() {
        System.out.println("Введите имя:");
        String name = readStringProperty(UserValidator::validateAndNormalizeName);
        System.out.println("С именем " + name + " пользователей: "
                + countService.countOccurrences(controller.getAllUsers(), user -> user.getName().equals(name)));
    }

    private void countingEmail() {
        System.out.println("Введите email:");
        String email = readStringProperty(UserValidator::validateAndNormalizeEmail);
        System.out.println("С email " + email + " пользователей: "
                + countService.countOccurrences(controller.getAllUsers(), user -> user.getEmail().equals(email)));
    }

    private void countingBirthYear() {
        System.out.println("Введите год рождения:");
        final Integer birthYear = readInt(UserValidator::validateBirthYear);

        System.out.println(birthYear + " г.р. пользователей: "
                + countService.countOccurrences(controller.getAllUsers(),
                        user -> birthYear.equals(user.getBirthYear())));
    }

    private void countingUser() {
        System.out.println("Введите имя:");
        String name = readStringProperty(UserValidator::validateAndNormalizeName);
        System.out.println("Введите email:");
        String email = readStringProperty(UserValidator::validateAndNormalizeEmail);
        System.out.println("Введите год рождения:");
        final Integer birthYear = readInt(UserValidator::validateBirthYear);

        System.out.println(String.format("Пользователей с именем %s с email %s %d г.р.: %d",
                name,
                email,
                birthYear,
                countService.countOccurrences(controller.getAllUsers(), user -> user.equals(new User.Builder()
                        .name(name)
                        .email(email)
                        .birthYear(birthYear)
                        .build()))));
    }
}