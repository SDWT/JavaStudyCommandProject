package com.example.sortapp.ui;

import com.example.sortapp.service.SortService;

import java.util.Scanner;

public class ConsoleUI {

    private final SortService service = new SortService();

    public void start() {
        // Пример простой реализации меню
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> service.handleRandom();   // Генерация
                case 2 -> service.handleFile();     // Из файла
                case 3 -> service.handleManual();   // Ручной ввод
                case 0 -> {
                    System.out.println("Exit...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void printMenu() {
        System.out.println("1. Random");
        System.out.println("2. File");
        System.out.println("3. Manual");
        System.out.println("0. Exit");
    }
}