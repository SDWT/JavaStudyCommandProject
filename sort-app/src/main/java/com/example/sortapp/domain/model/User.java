package com.example.sortapp.domain.model;

import java.util.Comparator;
import java.util.Objects;


public class User implements Comparable<User> {
    private final String name;
    private final String email;
    private final int birthYear;


    private User(Builder userBuilder) {
        this.name = validateAndNormalizeName(userBuilder.name);
        this.email = Objects.requireNonNull(userBuilder.email, "email не может быть пустым")
                .trim();

        if (userBuilder.birthYear < 0) {
            throw new IllegalArgumentException("Год не может быть отрицательным");
        }
        if (userBuilder.birthYear > java.time.Year.now()
                .getValue()) {
            throw new IllegalArgumentException("Год превышает текущий");
        }
        this.birthYear = userBuilder.birthYear;

    }

    private String validateAndNormalizeName(String name) {
        String validatedName = Objects.requireNonNull(name, "name не может быть null")
                .trim();
        if (validatedName.length() < 2) {
            throw new IllegalArgumentException("Поле name должно состоять минимум из 2х символов");
        }

        return validatedName;
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
