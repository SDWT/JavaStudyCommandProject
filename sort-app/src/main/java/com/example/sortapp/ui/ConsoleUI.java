package com.example.sortapp.ui;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

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
                case 4 -> countingMenu();
                case 0 -> {
                    System.out.println("Выход из программы...");
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    // === МЕНЮ ПОЛЬЗОВАТЕЛЕЙ ===
    private void userMenu() {

        while (true) {
            System.out.println("""

                    === МЕНЮ ПОЛЬЗОВАТЕЛЕЙ ===
                    1. Сгенерировать пользователей
                    2. Загрузка из файла
                    3. Сохранение в файл
                    4. Ввести одного пользователя
                    5. Ввести несколько пользователей
                    9. Очистить список
                    0. Назад
                    """);

            switch (readInt()) {
                case 1 -> generate();
                case 2 -> load();
                case 3 -> save();
                case 4 -> manualInputSingle();
                case 5 -> manualInputMultiple();
                case 9 -> {
                    controller.clearUsers();
                    System.out.println("Список очищен.");
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    // === МЕНЮ СОРТИРОВКИ ===
    private void sortMenu() {

        System.out.println("""
                === МЕНЮ СОРТИРОВКИ ===
                Стратегия:
                1. Сортировка пузырьком
                2. Сортировка слиянием
                3. Сортировка по чётным birthYear
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

            controller.clearUsers();
            controller.addUsers(sorted);

            System.out.println("Пользователи отсортированы:");
            printUsers(sorted);

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка сортировки: " + e.getMessage());
        }
    }

    // === ВВОД ОДНОГО ПОЛЬЗОВАТЕЛЯ ===

    private void manualInputSingle() {

        System.out.println("Введите имя:");
        String name = readValidatedString(UserValidator::validateAndNormalizeName);

        System.out.println("Введите email:");
        String email = readValidatedString(UserValidator::validateAndNormalizeEmail);

        System.out.println("Введите год рождения:");
        int birthYear = readValidatedInt(UserValidator::validateBirthYear);

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

            String answer = scanner.nextLine();

            if (!answer.equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    // === ВЫВОД ПОЛЬЗОВАТЕЛЕЙ ===
    private void printUsers(List<User> users) {

        if (users.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }

        int index = 1;

        for (User user : users) {
            System.out.println(index++ + ". " + user);
        }
    }

    // === ЗАГРУЗКА ИЗ ФАЙЛА ===
    private void load() {

        System.out.println("Введите путь к файлу:");
        String path = scanner.nextLine();

        try {
            controller.clearUsers();

            List<User> users = controller.loadFromFile(path);

            controller.addUsers(users);

            System.out.println("Загружено пользователей: " + users.size());

        } catch (Exception e) {
            System.out.println("Ошибка загрузки файла: " + e.getMessage());
        }
    }

    // === СОХРАНЕНИЕ В ФАЙЛ ===
    private void save() {

        System.out.println("Введите путь к файлу:");
        String path = scanner.nextLine();

        try {

            controller.saveToFile(path, controller.getAllUsers());
            System.out.println("Данные сохранены.");

        } catch (Exception e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    // === ГЕНЕРАЦИЯ ===
    private void generate() {

        int size;
        do {

            System.out.println("Введите количество пользователей:");

            size = readInt();

            if (size < 0) {
                System.out.println("Число должно быть >= 0");
            }

        } while (size < 0);

        List<User> users = UserGenerator.generate(size);

        controller.addUsers(users);

        System.out.println("Сгенерировано пользователей: " + users.size());
    }

    // === МЕНЮ ПОДСЧЁТА ===
    private void countingMenu() {

        while (true) {

            System.out.println("""
                    === МЕНЮ ПОДСЧЁТА ===
                    Поле сравнения:
                    1. Имя
                    2. Электронная почта
                    3. Год рождения
                    4. Полное совпадение
                    0. Назад
                    """);

            switch (readInt()) {
                case 1 -> countingName();
                case 2 -> countingEmail();
                case 3 -> countingBirthYear();
                case 4 -> countingUser();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private void countingName() {

        System.out.println("Введите имя:");
        String name = readValidatedString(UserValidator::validateAndNormalizeName);

        long count = countService.countOccurrences(controller.getAllUsers(),
                user -> user.getName().equals(name));

        System.out.println("Пользователей с именем " + name + ": " + count);
    }

    private void countingEmail() {

        System.out.println("Введите email:");
        String email = readValidatedString(UserValidator::validateAndNormalizeEmail);

        long count = countService.countOccurrences(controller.getAllUsers(),
                user -> user.getEmail().equals(email));

        System.out.println("Пользователей с email " + email + ": " + count);
    }

    private void countingBirthYear() {

        System.out.println("Введите год рождения:");
        Integer birthYear = readValidatedInt(UserValidator::validateBirthYear);

        long count = countService.countOccurrences(controller.getAllUsers(), user -> birthYear.equals(user.getBirthYear()));

        System.out.println("Пользователей " + birthYear + " г.р.: " + count);
    }

    private void countingUser() {

        System.out.println("Введите имя:");
        String name = readValidatedString(UserValidator::validateAndNormalizeName);
        System.out.println("Введите email:");
        String email = readValidatedString(UserValidator::validateAndNormalizeEmail);
        System.out.println("Введите год рождения:");
        int birthYear = readValidatedInt(UserValidator::validateBirthYear);

        User target = new User.Builder()
                .name(name)
                .email(email)
                .birthYear(birthYear)
                .build();

        long count = countService.countOccurrences(controller.getAllUsers(), user -> user.equals(target));

        System.out.println("Найдено пользователей: " + count);
    }

    // === INPUT HELPERS ===

    private int readInt() {

        while (true) {
            try {

                return Integer.parseInt(scanner.nextLine());

            } catch (Exception e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    private int readValidatedInt(Function<Integer, Integer> validator) {

        while (true) {
            try {

                return validator.apply(readInt());

            } catch (Exception e) {
                System.out.println("Ошибка валидации: " + e.getMessage());
            }
        }
    }

    private String readValidatedString(Function<String, String> validator) {

        while (true) {
            try {

                return validator.apply(scanner.nextLine());

            } catch (Exception e) {
                System.out.println("Ошибка валидации: " + e.getMessage());
            }
        }
    }
}