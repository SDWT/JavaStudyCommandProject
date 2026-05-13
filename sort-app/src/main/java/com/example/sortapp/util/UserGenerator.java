package com.example.sortapp.util;

import java.time.Year;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.example.sortapp.domain.model.User;

public final class UserGenerator {

    private static final int MIN_YEAR = 1900;

    private UserGenerator() {}

    public static List<User> generate(int size) {
        return generate(size, new Random());
    }

    public static List<User> generate(int size, Random random) {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be >= 0");
        }

        return IntStream.range(0, size)
                .mapToObj(i -> generateOne(random))
                .toList();
    }

    private static User generateOne(Random random) {
        String name = "user" + random.nextInt(10);

        int currentYear = Year.now().getValue();

        return new User.Builder()
                .name(name)
                .email(name + "@example.com")
                .birthYear(random.nextInt(MIN_YEAR, currentYear + 1))
                .build();
    }
}