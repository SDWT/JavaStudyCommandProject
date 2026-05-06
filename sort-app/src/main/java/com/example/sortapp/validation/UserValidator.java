package com.example.sortapp.validation;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserValidator {
    private static final Pattern  NAME_PATTERN = Pattern.compile("^[А-ЯA-Zа-яa-z][А-ЯA-Zа-яa-z\\s'-]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[A-Za-z0-9._-]+@[a-z]+\\.[A-Za-z]{2,}$");

    public static String validateAndNormalizeEmail(String email) {
        String validatedEmail = Objects.requireNonNull(email, "email не может быть пустым")
                .trim();
        Matcher emailMatcher = EMAIL_PATTERN.matcher(validatedEmail);
        if (!emailMatcher.matches()) {
            throw new IllegalArgumentException("Некорректная почта");
        }
        return validatedEmail.toLowerCase();
    }

    public static String validateAndNormalizeName(String name) {
        String validatedName = Objects.requireNonNull(name, "name не может быть null")
                .trim();
        if (validatedName.length() < 2) {
            throw new IllegalArgumentException("Поле name должно состоять минимум из 2х символов");
        }

        Matcher nameMatcher = NAME_PATTERN.matcher(validatedName);
        if (!nameMatcher.matches()) {
            throw new IllegalArgumentException("Поле name не может содержать цифры и спец символы");
        }

        return validatedName;
    }

    public static int validateBirthYear(int birthYear) {
        int minYear = 1900;
        int currentYear = java.time.Year.now()
                .getValue();
        if (birthYear < 0) {
            throw new IllegalArgumentException("Год не может быть отрицательным");
        }
        if (birthYear < minYear || birthYear > currentYear) {
            throw new IllegalArgumentException("Год рождения: 1900–" + currentYear);
        }
        return birthYear;
    }
}