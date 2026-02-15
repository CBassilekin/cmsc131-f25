package projects.hospital;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NameTest {

    private String first;
    private String last;
    private Name name;

    /**
     * Setting up our Name object to be used with each test. First and last name are
     * set
     * for testing the getFirstName() and getLastName() methods.
     * 
     */
    @BeforeEach
    void setupName() {
        first = "John";
        last = "Smith";
    }

    @Test
    void testConstructorWithNotNullParameters() {
        name = new Name(first, last);

        // should create a Name object with the given first and last name
        assertEquals(first, name.getFirstName());
        assertEquals(last, name.getLastName());

        // should not throw an exception and should create a Name object with the given
        // first and last name
        assertNotNull(name.getFirstName());
        assertNotNull(name.getLastName());
    }

    /**
     * Test confirms that the constructor of the Name class throws an exception when
     * null parameters are passed for the first name or last name.
     * 
     * 
     */
    @Test
    void testConstructorWithNullParameters() {

        // should throw an IllegalArgumentException when the first name is null
        Exception e1 = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Name(null, "Smith");
                });
        assertEquals("Any part of the name cannot be null or empty", e1.getMessage());

        // should throw an IllegalArgumentException when the last name is null
        Exception e2 = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Name("John", null);
                });
        assertEquals("Any part of the name cannot be null or empty", e2.getMessage());

        // should throw an IllegalArgumentException when both first and last names are
        // null
        Exception e3 = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Name(null, null);
                });
        assertEquals("Any part of the name cannot be null or empty", e3.getMessage());
    }

    /**
     * Test confirms that getFirstName() method returns the correct first name of
     * the patient.
     * 
     */
    @Test
    void testGetFirstName() {
        name = new Name(first, last);
        String expectedFirstName = "John";
        String actualFirstName = name.getFirstName();
        assertEquals(expectedFirstName, actualFirstName);
        // assertEquals(first, name.getFirstName());
    }

    /**
     * Test confirms that getLastName() method returns the correct last name of the
     * patient.
     */
    @Test
    void testGetLastName() {
        name = new Name(first, last);
        String expectedLastName = "Smith";
        String actualLastname = name.getLastName();
        assertEquals(expectedLastName, actualLastname);
        // assertEquals(last, name.getLastName());
    }

    @Test
    void testMatchReturnsTrueOrFalse() {
        name = new Name(first, last);

        Name nameTest1 = new Name("john", "Smith");
        Name nameTest2 = new Name("John", "smith");
        Name nameTest3 = new Name("John", "Doe");
        Name nameTest4 = new Name("Jane", "Doe");

        assertTrue(name.match(nameTest1));// should return true because the first and last names match, ignoring case
        assertTrue(name.match(nameTest2));// should return true because the first and last names match, ignoring case
        assertFalse(name.match(nameTest3));// should return false because the last names do not match
        assertFalse(name.match(nameTest4)); // should return false because the first and last names do not match
    }

    /**
     * Test confirms that match() method throws an exception when a null Name object
     * is
     * passed as an argument.
     * 
     */
    @Test
    void testMatchWithNullName() {
        name = new Name(first, last);

        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    name.match(null);
                });
        assertEquals("Cannot compare with null Name object.", e.getMessage());
    }

    /**
     * Test confirms that isLessThan() method returns true if the current Name
     * object is less than the other Name object based on last name and first name,
     * and false otherwise.
     * 
     */
    @Test
    void testIsLessThanReturnsTrueOrFalse() {
        name = new Name(first, last);
        Name nameTest1 = new Name("Jane", "smith");
        Name nameTest2 = new Name("John", "Doe");
        Name nameTest3 = new Name("John", "Smith");
        Name nameTest4 = new Name("John", "Vladimir");

        assertFalse(name.isLessThan(nameTest1)); // should return true because "John" comes before "Jane" alphabetically
        assertFalse(name.isLessThan(nameTest2)); // should return true because "Smith" comes after "Doe" alphabetically
        assertFalse(name.isLessThan(nameTest3)); // should return false because both names are the same}
        assertTrue(name.isLessThan(nameTest4)); // should return true because "Smith" comes before "Vladimir"
                                                // alphabetically
    }

    /**
     * Test confirms that isLessThan() method throws an exception when a null Name
     * object is passed as an argument.
     * 
     */
    @Test
    void testIsLessThanWithNull() {

        // we can't have a null Name object to compare with,
        // so we expect an IllegalArgumentException to be thrown
        name = new Name(first, last);
        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    name.isLessThan(null);
                });
        assertEquals("Other name cannot be null", e.getMessage());
    }

    /**
     * Test confirms that toString() method
     * returns a string representation of the Name
     * object in the format "firstName lastName".
     */
    @Test
    void testToString() {
        name = new Name(first, last);
        String expectedString = name.getFirstName() + " " + name.getLastName();
        String actualString = name.toString();

        assertEquals(expectedString, actualString);
    }

}