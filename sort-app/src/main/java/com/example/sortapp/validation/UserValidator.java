package com.example.sortapp.validation;

import java.time.Year;
import java.util.Objects;
import java.util.regex.Pattern;

public final class UserValidator {
    private static final int MIN_YEAR = 1900;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[А-ЯA-Zа-яa-z]+([\\s'-][А-ЯA-Za-zа-я]+)*$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private UserValidator() {
    }

    public static String validateAndNormalizeEmail(String email) {

        String normalized = Objects.requireNonNull(email, "email не может быть null")
                .trim()
                .toLowerCase();

        if (normalized.isBlank())
            throw new IllegalArgumentException("Email не может быть пустым");

        if (!EMAIL_PATTERN.matcher(normalized).matches())
            throw new IllegalArgumentException("Некорректная почта");

        return normalized;
    }

    public static String validateAndNormalizeName(String name) {
        String normalized = Objects.requireNonNull(name, "name не может быть null")
                .trim();

        if (normalized.length() < 2)
            throw new IllegalArgumentException("Name too short");

        if (!NAME_PATTERN.matcher(normalized).matches())
            throw new IllegalArgumentException("Invalid name format");

        return normalized;
    }

    public static int validateBirthYear(int birthYear) {

        int currentYear = Year.now().getValue();

        if (birthYear < MIN_YEAR || birthYear > currentYear)
            throw new IllegalArgumentException("Некорректный год рождения: " + birthYear +
                    ". Допустимый диапазон: от " + MIN_YEAR + " до " + currentYear + " включительно");

        return birthYear;
    }
}