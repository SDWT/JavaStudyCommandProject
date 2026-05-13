package com.example.sortapp.util;

import com.example.sortapp.domain.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class UserGeneratorTest {

    @Test
    void shouldGenerateCorrectAmount() {

        List<User> users = UserGenerator.generate(5);

        assertEquals(5, users.size());
    }

    @Test
    void shouldGenerateValidUsers() {

        List<User> users = UserGenerator.generate(10, new Random(1));

        for (User user : users) {
            assertNotNull(user.getName());
            assertNotNull(user.getEmail());
            assertTrue(user.getBirthYear() >= 1900);
        }
    }

    @Test
    void shouldThrowExceptionForNegativeSize() {

        assertThrows(IllegalArgumentException.class, () -> UserGenerator.generate(-1));
    }
}