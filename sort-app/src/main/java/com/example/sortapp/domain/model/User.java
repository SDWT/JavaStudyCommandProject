package com.example.sortapp.domain.model;

import java.util.Comparator;
import java.util.Objects;

// Добавлен класс как заглушка, если выберем другой. то переименум классы заглушки
public class User implements Comparable<User>{
    private final String name;
    private final String email;
    private final int birthYear;


    public User(String name, String email, int birthYear) {
        this.name = name;
        this.email = email;
        this.birthYear = birthYear;
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
        return  Comparator.comparing(User::getName)
                .thenComparing(User::getEmail)
                .thenComparingInt(User::getBirthYear)
                .compare(this, other);
    }
}
