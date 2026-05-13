package com.example.sortapp.strategy.impl;

import com.example.sortapp.domain.model.User;
import com.example.sortapp.strategy.SortStrategy;
import com.example.sortapp.util.UserGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EvenBirthYearSortStrategyTest {

    @Test
    void shouldSortOnlyUsersWithEvenBirthYear() {

        List<User> users = new ArrayList<>(UserGenerator.generate(20, new Random(30)));

        List<User> original = new ArrayList<>(users);

        SortStrategy<User> strategy = new EvenBirthYearSortStrategy(new BubbleSort<>());

        strategy.sort(users, User.BY_BIRTH_YEAR);

        // Проверяем, что пользователи с нечётным годом остались на своих местах
        for (int i = 0; i < users.size(); i++) {

            int birthYear = original.get(i).getBirthYear();

            if (birthYear % 2 != 0)
                assertEquals(original.get(i), users.get(i));
        }

        // Проверяем, что чётные birthYear отсортированы

        List<Integer> evenYears = users.stream()
                .map(User::getBirthYear)
                .filter(year -> year % 2 == 0)
                .toList();

        for (int i = 0; i < evenYears.size() - 1; i++)
            assertTrue(evenYears.get(i) <= evenYears.get(i + 1));
    }

    @Test
    void shouldKeepOddUsersOnSamePositions() {

        List<User> users = new ArrayList<>(UserGenerator.generate(30, new Random(30)));

        List<User> original = new ArrayList<>(users);

        SortStrategy<User> strategy = new EvenBirthYearSortStrategy(new MergeSort<>());

        strategy.sort(users, User.BY_NAME);

        for (int i = 0; i < users.size(); i++) {

            User originalUser = original.get(i);

            if (originalUser.getBirthYear() % 2 != 0)
                assertEquals(originalUser, users.get(i));
        }
    }
}