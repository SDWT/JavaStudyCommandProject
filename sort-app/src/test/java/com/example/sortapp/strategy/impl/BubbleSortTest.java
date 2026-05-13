package com.example.sortapp.strategy.impl;

import com.example.sortapp.domain.model.User;
import com.example.sortapp.strategy.SortStrategy;
import com.example.sortapp.util.UserGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {

    @Test
    void shouldSortUsersByBirthYear() {

        List<User> users = new ArrayList<>(UserGenerator.generate(20, new Random(30)));

        SortStrategy<User> strategy = new BubbleSort<>();

        strategy.sort(users, User.BY_BIRTH_YEAR);

        for (int i = 0; i < users.size() - 1; i++)
            assertTrue(users.get(i).getBirthYear() <= users.get(i + 1).getBirthYear());
    }

    @Test
    void shouldSortUsersByName() {

        List<User> users = new ArrayList<>(UserGenerator.generate(20, new Random(30)));

        SortStrategy<User> strategy = new BubbleSort<>();

        strategy.sort(users, User.BY_NAME);

        for (int i = 0; i < users.size() - 1; i++)
            assertTrue(users.get(i).getName().compareTo(users.get(i + 1).getName()) <= 0);
    }
}