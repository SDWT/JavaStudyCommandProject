package com.example.sortapp.domain.model;

import com.example.sortapp.validation.UserValidator;

import java.util.Comparator;
import java.util.Objects;


public class User implements Comparable<User> {

    private final String name;
    private final String email;
    private final int birthYear;


    private User(Builder userBuilder) {
        this.name = UserValidator.validateAndNormalizeName(userBuilder.name);
        this.email = UserValidator.validateAndNormalizeEmail(userBuilder.email);
        this.birthYear = UserValidator.validateBirthYear(userBuilder.birthYear);
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

        public Builder email(String email){
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
