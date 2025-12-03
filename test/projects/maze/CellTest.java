package projects.maze;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class CellTest {

    private Cell cell;

    @Test
    public void testConstructor() {
        Coords expectedCoords = new Coords(1, 1);
        CellStatus expectedStatus = CellStatus.S;
        cell = new Cell(expectedCoords, expectedStatus);
        assertNotNull(cell);

    }

    @Test
    public void ConstructordataValidationThrows() {

        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Cell(null, CellStatus.O);
                });
        assertEquals("this parameter cannot be empty.", e.getMessage());

        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Cell(new Coords(0, 0), null);
                });

        assertEquals("this parameter cannot be empty.", e.getMessage());
        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Cell(null, null);
                });
        assertEquals("this parameter cannot be empty.", e.getMessage());

    }

    /**
     * test method to verfies correct coordinates is returned
     */
    @Test
    public void getCoordsReturnsCorrectValue() {
        cell = new Cell(new Coords(0, 0), CellStatus.O);
        Coords expectedCoords = new Coords(0, 0);

        assertTrue(expectedCoords.equals(cell.getCoords()));
    }

    /**
     * test method to verfies correct Status is returned
     */
    @Test
    public void getStatusReturnsCorrectValue() {
        cell = new Cell(new Coords(0, 0), CellStatus.O);
        CellStatus expectedStatus = CellStatus.O;

        assertEquals(expectedStatus, cell.getStatus());
    }

    /**
     * test method to verfies traversal Flag returns false.
     */
    @Test
    public void getTraversalFlagReturnsFalseByDefault() {
        cell = new Cell(new Coords(0, 0), CellStatus.S);
        assertFalse(cell.getTraversalFlag());
    }

}