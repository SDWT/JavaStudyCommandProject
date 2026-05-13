package com.example.sortapp.domain.model;

import com.example.sortapp.validation.UserValidator;

import java.util.Comparator;
import java.util.Objects;

public final class User implements Comparable<User> {

    public static final Comparator<User> BY_NAME = Comparator.comparing(User::getName);
    public static final Comparator<User> BY_EMAIL = Comparator.comparing(User::getEmail);
    public static final Comparator<User> BY_BIRTH_YEAR = Comparator.comparingInt(User::getBirthYear);
    public static final Comparator<User> BY_NAME_EMAIL_BIRTH_YEAR = Comparator.comparing(User::getName)
            .thenComparing(User::getEmail)
            .thenComparingInt(User::getBirthYear);

    private final String name;
    private final String email;
    private final int birthYear;

    private User(Builder builder) {
        this.name = UserValidator.validateAndNormalizeName(builder.name);
        this.email = UserValidator.validateAndNormalizeEmail(builder.email);
        this.birthYear = UserValidator.validateBirthYear(builder.birthYear);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getBirthYear() {
        return birthYear;
    }

    @Override
    public int compareTo(User other) {
        return BY_NAME_EMAIL_BIRTH_YEAR.compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        
        User user = (User) o;
        return birthYear == user.birthYear && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, birthYear);
    }

    @Override
    public String toString() {
        return "User{name='%s', email='%s', birthYear=%d}"
                .formatted(name, email, birthYear);
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