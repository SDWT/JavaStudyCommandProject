package com.example.sortapp.domain.model;

import java.util.Comparator;
import java.util.Objects;

// Добавлен класс как заглушка, если выберем другой. то переименум классы заглушки
public class User implements Comparable<User>{
    private final String name;
    private final String email;
    private final int birthYear;


    private User(UserBuilder userBuilder){
        this.name = userBuilder.name;
        this.email = userBuilder.email;
        this.birthYear = userBuilder.birthYear;
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


    public static class UserBuilder{
        private String name;
        private String email;
        private int birthYear;

        public UserBuilder setName(String name){
            this.name = name;
            return this;
        }

        public UserBuilder setEmail(String email){
            this.email = email;
            return this;
        }

        public UserBuilder setBirthYear(int birthYear){
            this.birthYear = birthYear;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }
}
