package com.example.sortapp.service;

import com.example.sortapp.domain.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountServiceTest {

        private final CountService service = new CountService();

        @Test
        void shouldCountOccurrencesByPredicate() {

                List<User> users = List.of(
                                createUser("Alex"),
                                createUser("Bob"),
                                createUser("Alex"));

                int result = service.countOccurrences(users, user -> user.getName().equals("Alex"));

                assertEquals(2, result);
        }

        @Test
        void shouldReturnZeroWhenNoMatches() {

                List<User> users = List.of(
                                createUser("Bob"),
                                createUser("Mike"));

                int result = service.countOccurrences(users, user -> user.getName().equals("Alex"));

                assertEquals(0, result);
        }

        @Test
        void shouldReturnZeroForEmptyList() {

                int result = service.countOccurrences(List.of(), user -> true);

                assertEquals(0, result);
        }

        @Test
        void shouldThrowExceptionForNullData() {

                assertThrows(NullPointerException.class,
                        () -> service.countOccurrences(null, user -> true));
        }

        @Test
        void shouldThrowExceptionForNullPredicate() {

                assertThrows(NullPointerException.class,
                        () -> service.countOccurrences(List.of(), null));
        }

        private User createUser(String name) {

                return new User.Builder()
                                .name(name)
                                .email(name + "@mail.com")
                                .birthYear(2000)
                                .build();
        }
}