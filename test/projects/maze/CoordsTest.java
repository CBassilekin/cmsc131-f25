package projects.maze;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoordsTest {
    private Coords testCoords;

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

    /**
     * test method to verify that correct row is returned
     */
    @Test
    public void getRowReturnsCorrectRow() {
        testCoords = new Coords(5, 0);
        double expectedRow = (double) 5;
        double actualRow = (double) testCoords.getRow();

        assertEquals(expectedRow, actualRow);
    }

    /**
     * test method to verify that correct col is returned
     */
    @Test
    public void getColReturnsCorrectCol() {
        testCoords = new Coords(0, 0);
        double expectedCol = (double) 0;
        double actualCol = (double) testCoords.getCol();

        assertEquals(expectedCol, actualCol);
    }

    @Test
    public void testEqualsIdenticalObjectsReturnTrue() {
        testCoords = new Coords(0, 0);
        Coords test2 = new Coords(0, 0);
        assertTrue(testCoords.equals(test2));

    }

    @Test
    public void testEqualssameObject() {
        testCoords = new Coords(0, 0);
        assertTrue(testCoords.equals(testCoords));

    }

    @Test
    public void testEqualsDifferentObjects() {
        testCoords = new Coords(0, 0);
        Coords test2 = new Coords(1, 0);
        assertFalse(testCoords.equals(test2));
    }

    @Test
    public void testEqualsDifferentRow() {
        testCoords = new Coords(1, 0);
        Coords test2 = new Coords(2, 0);
        assertFalse(testCoords.equals(test2));

    }

    @Test
    public void testEqualsDifferentCol() {
        testCoords = new Coords(1, 0);
        Coords test2 = new Coords(1, 1);
        assertFalse(testCoords.equals(test2));
    }

    @Test
    public void testEqualsDifferentRowandCol() {
        testCoords = new Coords(1, 0);
        Coords test2 = new Coords(2, 1);
        assertFalse(testCoords.equals(test2));
    }

    @Test
    public void testEqualsnull() {
        testCoords = new Coords(1, 0);
        assertFalse(testCoords.equals(null));
    }
}