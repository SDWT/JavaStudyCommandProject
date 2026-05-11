package com.example.sortapp.validation;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    @ParameterizedTest
    @CsvSource({
            "'  Test@mail.com  ', test@mail.com",
            "' test@gmail.com', test@gmail.com",
            "'test-test@mail.com ', test-test@mail.com",
            "1234t@yandex.ru, 1234t@yandex.ru",
            "test.user@gmail.com, test.user@gmail.com",
            "user123@mail.co.uk, user123@mail.co.uk",
            "USER@GMAIL.COM, user@gmail.com",
            "test@mail.mail.com, test@mail.mail.com"
    })
    void shouldReturnValidEmail(String input, String expected) {
        String result = UserValidator.validateAndNormalizeEmail(input);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "John.yandex.ru",
            "test.test@yandex@yandex.ru",
            "!!anna!!@gmail.com",
            "",
            "   "
    })

    void shouldThrowExceptionForInvalidEmails(String invalidEmail){
        assertThrows(
                IllegalArgumentException.class,
                () -> UserValidator.validateAndNormalizeEmail(invalidEmail)
        );
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull(){
        assertThrows(
                NullPointerException.class,
                () -> UserValidator.validateAndNormalizeEmail(null)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "John1",
            "123",
            "Mike@",
            "-Alex",
            "A",
            "  A  ",
            "Жанна!",
            "Bob--",
            "bob-",
            "",
            " ",
            "'Connor"
    })
    void shouldThrowExceptionForInvalidNames(String invalidName) {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserValidator.validateAndNormalizeName(invalidName)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "'  Anna ', Anna",
            "Anna-Maria, Anna-Maria",
            "'      O''Connor', O'Connor",
            "'Anna Maria   ', Anna Maria",
            "'Д''Артаньян ', Д'Артаньян"
    })
    void shouldReturnValidName(String input, String expected){
        String result = UserValidator.validateAndNormalizeName(input);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @NullSource
    void shouldThrowExceptionWhenNameIsNull(String name){
        NullPointerException exception =
        assertThrows(
                NullPointerException.class,
                () -> UserValidator.validateAndNormalizeName(name)
        );

        assertEquals("name не может быть null", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 3, 5, -3, 15, Integer.MAX_VALUE, 00022, 30304, 20200, 1899, 1000, 2027, -1990 })
    void shouldThrowExceptionForInvalidBirthYear(int invalidYear) {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserValidator.validateBirthYear(invalidYear)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1900, 1990, 2000, 2025, 2026})
    void shouldReturnValidBirthYear(int validYear) {
        int result = UserValidator.validateBirthYear(validYear);

        assertEquals(validYear, result);
    }


    @Test
    void shouldReturnCurrentYearAsValid() {
        int currentYear = Year.now().getValue();

        assertEquals(
                currentYear,
                UserValidator.validateBirthYear(currentYear)
        );
    }
}