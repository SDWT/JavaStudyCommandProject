package com.example.sortapp.controller;

import com.example.sortapp.domain.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortControllerTest {

    @Test
    void shouldAddUser() {

        SortController controller = new SortController();

        User user = createUser("Alex");

        controller.addUser(user);

        assertEquals(1, controller.getUsersCount());
    }

    @Test
    void shouldClearUsers() {

        SortController controller = new SortController();

        controller.addUser(createUser("Alex"));

        controller.clearUsers();

        assertEquals(0, controller.getUsersCount());
    }

    @Test
    void shouldSortUsers() {

        SortController controller = new SortController();

        List<User> users = List.of(
                createUser("Charlie"),
                createUser("Alex"));

        List<User> sorted = controller.sort(users, 1, 1);

        assertEquals("Alex", sorted.get(0).getName());
    }

    private User createUser(String name) {

        return new User.Builder()
                .name(name)
                .email(name + "@mail.com")
                .birthYear(2000)
                .build();
    }
}