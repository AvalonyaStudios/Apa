package eu.avalonya.api.command.arguments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChoiceArgumentTest {

    private ChoiceArgument argument;

    @BeforeEach
    void setUp() {
        argument = new ChoiceArgument(
                List.of("one", "two", "three")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"Two", "ONE", "four"})
    void testBadInput(String input) {
        assertFalse(argument.test(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"one", "two", "three"})
    void testValidInput(String input) {
        assertTrue(argument.test(input));
    }

    @Test
    void testGetCompletion() {
        assertIterableEquals(List.of("one", "two", "three"), argument.getCompletions());
    }

    @Test
    void testGetUsage() {
        assertEquals("one|two|three", argument.getUsage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"one", "two"})
    void testGetValue(String value) {
        argument.setInput(value);

        assertEquals(value, argument.get());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testSetRequired(Boolean value) {
        argument.setRequired(value);

        assertEquals(value, argument.isRequired());
    }
  
}