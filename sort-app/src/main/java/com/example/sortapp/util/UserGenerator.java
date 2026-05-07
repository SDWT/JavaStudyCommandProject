package com.example.sortapp.util;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.sortapp.domain.model.User;

public class UserGenerator {

    public static List<User> generate(int size) {
        return generate(size, new Random());
    }

    public static List<User> generate(int size, Random random) {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be >= 0");
        }

        List<User> users = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            users.add(generateOne(random));
        }

        return users;
    }

    private static User generateOne(Random random) {
        String name = "user" + random.nextInt(10);

        int currentYear = Year.now().getValue();

        return new User.Builder()
                .name(name)
                .email(name + "@example.com")
                .birthYear(random.nextInt(1900, currentYear))
                .build();
    }
}
