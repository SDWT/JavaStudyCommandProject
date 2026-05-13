package com.example.sortapp.service;

import com.example.sortapp.domain.model.User;
import com.example.sortapp.strategy.impl.BubbleSort;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortServiceTest {

    @Test
    void shouldSortUsersByName() {

        SortService<User> service = new SortService<>();

        service.setStrategy(new BubbleSort<>());

        List<User> users = new ArrayList<>();

        users.add(createUser("Charlie"));
        users.add(createUser("Alex"));
        users.add(createUser("Bob"));

        List<User> sorted = service.sort(users, User.BY_NAME);

        assertEquals("Alex", sorted.get(0).getName());

        assertEquals("Bob", sorted.get(1).getName());

        assertEquals("Charlie", sorted.get(2).getName());
    }

    @Test
    void shouldNotMutateOriginalList() {

        SortService<User> service = new SortService<>();

        service.setStrategy(new BubbleSort<>());

        List<User> users = new ArrayList<>();

        users.add(createUser("Charlie"));
        users.add(createUser("Alex"));

        service.sort(users, User.BY_NAME);

        assertEquals("Charlie", users.get(0).getName());
    }

    @Test
    void shouldThrowExceptionWhenStrategyNotSet() {

        SortService<User> service = new SortService<>();

        assertThrows(IllegalStateException.class,
                () -> service.sort(List.of(), User.BY_NAME));
    }

    private User createUser(String name) {

        return new User.Builder()
                .name(name)
                .email(name + "@mail.com")
                .birthYear(2000)
                .build();
    }
}