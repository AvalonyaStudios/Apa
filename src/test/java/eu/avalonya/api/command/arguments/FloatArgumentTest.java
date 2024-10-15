package eu.avalonya.api.command.arguments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FloatArgumentTest {

    private FloatArgument argument;

    @BeforeEach
    void setUp() {
        argument = new FloatArgument();
    }

    @ParameterizedTest
    @ValueSource(strings = {"0,5", "bad"})
    void testBadInput(String input) {
        assertFalse(argument.test(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "-56", "1.5", "-5.8"})
    void testValidInput(String input) {
        assertTrue(argument.test(input));
    }

    @Test
    void testGetCompletion() {
        assertEquals(0, argument.getCompletions().size());
    }

    @Test
    void testGetUsage() {
        assertEquals("float", argument.getUsage());
    }

    @ParameterizedTest
    @ValueSource(floats = {1.5f, 7, -8.5f, -4})
    void testGetValue(Float value) {
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