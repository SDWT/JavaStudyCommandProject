package com.example.sortapp.repository;

import com.example.sortapp.domain.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

        private static final String TEST_FILE = "test-users.txt";

        private final UserRepository repository = new UserRepository();

        @AfterEach
        void cleanUp() throws IOException {
                Files.deleteIfExists(Path.of(TEST_FILE));
        }

        @Test
        void shouldAddUser() {

                repository.add(createUser("Alex"));

                assertEquals(1, repository.size());
        }

        @Test
        void shouldAddAllUsers() {

                repository.addAll(List.of(
                                createUser("Alex"),
                                createUser("Bob")));

                assertEquals(2, repository.size());
        }

        @Test
        void shouldClearRepository() {

                repository.add(createUser("Alex"));

                repository.clear();

                assertTrue(repository.getAll().isEmpty());
        }

        @Test
        void shouldAppendUsersToFile() throws IOException {

                List<User> users = List.of(
                                createUser("Alex"),
                                createUser("Bob"));

                repository.appendToFile(TEST_FILE, users);

                List<String> lines = Files.readAllLines(Path.of(TEST_FILE));

                assertEquals(2, lines.size());

                assertTrue(lines.get(0).contains("Alex"));

                assertTrue(lines.get(1).contains("Bob"));
        }

        @Test
        void shouldReadUsersFromFile() {

                List<User> users = List.of(
                                createUser("Alex"),
                                createUser("Bob"));

                repository.appendToFile(TEST_FILE, users);

                List<User> loadedUsers = repository.readFromFile(TEST_FILE);

                assertEquals(2, loadedUsers.size());

                assertEquals("Alex", loadedUsers.get(0).getName());

                assertEquals("Bob", loadedUsers.get(1).getName());
        }

        @Test
        void shouldAppendDataInsteadOfOverwriting()
                        throws IOException {

                repository.appendToFile(TEST_FILE, List.of(createUser("Alex")));

                repository.appendToFile(TEST_FILE, List.of(createUser("Bob")));

                List<String> lines = Files.readAllLines(Path.of(TEST_FILE));

                assertEquals(2, lines.size());
        }

        @Test
        void shouldReturnEmptyListForEmptyFile()
                        throws IOException {

                Files.createFile(Path.of(TEST_FILE));

                List<User> users = repository.readFromFile(TEST_FILE);

                assertTrue(users.isEmpty());
        }

        @Test
        void shouldThrowExceptionForInvalidPath() {

                assertThrows(IllegalArgumentException.class,
                        () -> repository.readFromFile("invalid/path/file.txt"));
        }

        private User createUser(String name) {

                return new User.Builder()
                                .name(name)
                                .email(name.toLowerCase() + "@mail.com")
                                .birthYear(2000)
                                .build();
        }
}