package com.example.sortapp.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateValidUser() {

        User user = new User.Builder()
                .name("Alex")
                .email("alex@mail.com")
                .birthYear(2000)
                .build();

        assertEquals("Alex", user.getName());
        assertEquals("alex@mail.com", user.getEmail());
        assertEquals(2000, user.getBirthYear());
    }

    @Test
    void shouldNormalizeEmail() {

        User user = new User.Builder()
                .name("Alex")
                .email("  ALEX@mail.com ")
                .birthYear(2000)
                .build();

        assertEquals("alex@mail.com", user.getEmail());
    }

    @Test
    void shouldCreateEqualUsers() {

        User user1 = new User.Builder()
                .name("Alex")
                .email("alex@mail.com")
                .birthYear(2000)
                .build();

        User user2 = new User.Builder()
                .name("Alex")
                .email("alex@mail.com")
                .birthYear(2000)
                .build();

        assertEquals(user1, user2);

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void shouldCompareUsers() {

        User alex = new User.Builder()
                .name("Alex")
                .email("alex@mail.com")
                .birthYear(2000)
                .build();

        User bob = new User.Builder()
                .name("Bob")
                .email("bob@mail.com")
                .birthYear(2000)
                .build();

        assertTrue(alex.compareTo(bob) < 0);
    }

    @Test
    void shouldThrowExceptionForInvalidEmail() {

        assertThrows(IllegalArgumentException.class,
                () -> new User.Builder()
                        .name("Alex")
                        .email("invalid")
                        .birthYear(2000)
                        .build());
    }
}