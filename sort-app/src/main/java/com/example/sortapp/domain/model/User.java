package com.example.sortapp.domain.model;

import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class User implements Comparable<User> {
    private final String name;
    private final String email;
    private final int birthYear;


    private User(Builder userBuilder) {
        this.name = validateAndNormalizeName(userBuilder.name);
        this.email = validateAndNormalizeEmail(userBuilder.email);
        this.birthYear = validateBirthYear(userBuilder.birthYear);
    }

    private String validateAndNormalizeName(String name) {
        String validatedName = Objects.requireNonNull(name, "name не может быть null")
                .trim();
        if (validatedName.length() < 2) {
            throw new IllegalArgumentException("Поле name должно состоять минимум из 2х символов");
        }

        Pattern namePattern = Pattern.compile("^[А-ЯA-Zа-яa-z][А-ЯA-Zа-яa-z\\s'-]+$");
        Matcher nameMatcher = namePattern.matcher(validatedName);
        if(!nameMatcher.matches()){
            throw new IllegalArgumentException("Поле name не может содержать цифры и спец символы");
        }

        return validatedName;
    }

    private String validateAndNormalizeEmail(String email) {
        String validatedEmail = Objects.requireNonNull(email, "email не может быть пустым")
                .trim();
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._-]+@[a-z]+.[A-Za-z]{2,}$");
        Matcher emailMatcher = emailPattern.matcher(validatedEmail);
        if(!emailMatcher.matches()){
            throw new IllegalArgumentException("Некорректная почта");
        }
        return validatedEmail;
    }

    private int validateBirthYear(int birthYear) {
        int minYear = 1900;
        int currentYear = java.time.Year.now()
                .getValue();
        if (birthYear < 0) {
            throw new IllegalArgumentException("Год не может быть отрицательным");
        }
        if (birthYear < minYear || birthYear > currentYear ) {
            throw new IllegalArgumentException("Год рождения: 1900–" + currentYear);
        }
        return birthYear;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return birthYear == user.birthYear && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, birthYear);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }

    @Override
    public int compareTo(User other) {
        return Comparator.comparing(User::getName)
                .thenComparing(User::getEmail)
                .thenComparingInt(User::getBirthYear)
                .compare(this, other);
    }


    public static class Builder {
        private String name;
        private String email;
        private int birthYear;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder birthYear(int birthYear) {
            this.birthYear = birthYear;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
