package examples.helloworld;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import examples.helloworld.HelloWorld;

/**
 * Unit tests for HelloWorld class defined in exercises.week00.
 * Follows example https://docs.junit.org/current/user-guide/#writing-tests
 */
class HelloWorldTest {

    @Test
    public void testSingleHello() {
        assertEquals("Hello, World!", HelloWorld.getMessage(1));
    }

    @Test
    public void testMultipleHellos() {
        assertEquals(
            "Hello, World!" + System.lineSeparator() + "Hello, World!", 
            HelloWorld.getMessage(2)
        );
    }

    @Test
    public void testZeroCountThrows() {
        Exception exception = assertThrows(
            IllegalArgumentException.class, 
            () -> {HelloWorld.getMessage(0);}
        );
        assertEquals(
            "Count must be a positive integer.", 
            exception.getMessage()
        );
    }

    @Test
    public void testNegativeCountThrows() {
        Exception exception = assertThrows(
            IllegalArgumentException.class, 
            () -> {HelloWorld.getMessage(-5);}
        );
        assertEquals(
            "Count must be a positive integer.", 
            exception.getMessage()
        );
    }
}
