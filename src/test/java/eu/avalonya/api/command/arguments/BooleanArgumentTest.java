package eu.avalonya.api.command.arguments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BooleanArgumentTest {

    private BooleanArgument argument;

    @BeforeEach
    void setUp() {
        argument = new BooleanArgument();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "0", "bad"})
    void testBadInput(String input) {
        assertFalse(argument.test(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"true", "false", "True", "fAlse", "TRUE", "FALSE"})
    void testValidInput(String input) {
        assertTrue(argument.test(input));
    }

    @Test
    void testGetCompletion() {
        assertIterableEquals(List.of("true", "false"), argument.getCompletions());
    }

    @Test
    void testGetUsage() {
        assertEquals("boolean", argument.getUsage());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testGetValue(Boolean value) {
        argument.setInput(value.toString());

        assertEquals(value, argument.get());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testSetRequired(Boolean value) {
        argument.setRequired(value);

        assertEquals(value, argument.isRequired());
    }

}