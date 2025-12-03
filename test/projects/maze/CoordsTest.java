package projects.maze;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoordsTest {
    private Coords test;

    /**
     * test method for constructor
     */
    @Test
    public void testConstructor() {
        int expectedRow = 0;
        int expectedCol = 0;

        Coords coords = new Coords(expectedRow, expectedCol);
        assertNotNull(coords);
    }

    @Test
    public void ConstructordataValidationThrows() {

        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Coords(-1, 2);
                });
        assertEquals("this parameter cannot be empty.", e.getMessage());

        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Coords(0, -1);
                });

        assertEquals("this parameter cannot be empty.", e.getMessage());
        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Coords(-1, -1);
                });
        assertEquals("this parameter cannot be empty.", e.getMessage());

    }

    /**
     * test method to verfies correct row is returned
     */
    @Test
    public void getRowReturnsCorrectRow() {
        test = new Coords(5, 0);
        double expectedRow = (double) 5;
        double actualRow = (double) test.getRow();

        assertEquals(expectedRow, actualRow);
    }

    /**
     * test method to verfies correct col is returned
     */
    @Test
    public void getColReturnsCorrectCol() {
        test = new Coords(0, 0);
        double expectedCol = (double) 0;
        double actualCol = (double) test.getCol();

        assertEquals(expectedCol, actualCol);
    }

    @Test
    public void testEqualsIdenticalObjectsReturnTrue() {
        test = new Coords(0, 0);
        Coords test2 = new Coords(0, 0);
        assertTrue(test.equals(test2));

    }

    @Test
    public void testEqualssameObject() {
        test = new Coords(0, 0);
        assertTrue(test.equals(test));

    }

    @Test
    public void testEqualsDifferentObjects() {
        test = new Coords(0, 0);
        Coords test2 = new Coords(1, 0);
        assertFalse(test.equals(test2));
    }

    @Test
    public void testEqualsDifferentRow() {
        test = new Coords(1, 0);
        Coords test2 = new Coords(2, 0);
        assertFalse(test.equals(test2));

    }

    @Test
    public void testEqualsDifferentCol() {
        test = new Coords(1, 0);
        Coords test2 = new Coords(1, 1);
        assertFalse(test.equals(test2));
    }

    @Test
    public void testEqualsDifferentRowandCol() {
        test = new Coords(1, 0);
        Coords test2 = new Coords(2, 1);
        assertFalse(test.equals(test2));
    }

    @Test
    public void testEqualsnull() {
        test = new Coords(1, 0);
        assertFalse(test.equals(null));
    }
}