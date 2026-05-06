package com.example.sortapp.validation;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    @ParameterizedTest
    @CsvSource({
            "'  Test@mail.com  ', test@mail.com",
            "' test@gmail.com', test@gmail.com",
            "'test-test@mail.com ', test-test@mail.com",
            "1234t@yandex.ru, 1234t@yandex.ru"
    })
    void shouldReturnValidEmail(String input, String expected) {
        String result = UserValidator.validateAndNormalizeEmail(input);

        assertEquals(expected, result);
    }
}