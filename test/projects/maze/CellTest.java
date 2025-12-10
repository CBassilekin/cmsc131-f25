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

    /**
     * test method checks that neighbors to a cell in the grid are accurately
     * returned
     * by the getNeighbors() method.
     */
    @Test
    public void getNeighborsReturnsCorrectArray() {

        Maze maze = new Maze(4);
        Grid testGrid = new Grid(4);
        Cell[] cells = new Cell[] {
                new Cell(new Coords(0, 0), CellStatus.O),
                new Cell(new Coords(0, 1), CellStatus.O),
                new Cell(new Coords(1, 1), CellStatus.P),
                new Cell(new Coords(1, 0), CellStatus.O) };
        testGrid.insertCell(cells[0]);
        testGrid.insertCell(cells[1]);
        testGrid.insertCell(cells[2]);
        testGrid.insertCell(cells[3]);

        // Finding the expected neighbors of cell (new Coorrds (0,0), CellStatus.O)
        Coords[] expectedNeighbors = new Coords[] { new Coords(0, 1), null, new Coords(1, 0), null };

        // Finding the actual neighbors from the getNeighbors() method
        Cell lookUpCell = (testGrid.getAllCells()[0]);
        maze.discoverNeighborsInGrid(lookUpCell);
        Coords[] actualNeighbors = (lookUpCell.getNeighbors());

        assertNotNull(expectedNeighbors);
        assertNotNull(actualNeighbors);
        assertTrue(expectedNeighbors[0].equals(actualNeighbors[0]));
        assertTrue(expectedNeighbors[2].equals(actualNeighbors[2]));

        assertNull(actualNeighbors[1]);
        assertNull(actualNeighbors[3]);

    }

    /**
     * test method should return an empty array when a Cell not
     * present in the grid is passed onto the getNeighbors() method.
     */
    @Test
    public void getNeighborsReturnsEmptytArray() {

        Maze maze = new Maze(5);
        Grid testGrid = new Grid(5);
        Cell[] cells = new Cell[] {
                new Cell(new Coords(0, 0), CellStatus.O),
                new Cell(new Coords(0, 1), CellStatus.O),
                new Cell(new Coords(1, 1), CellStatus.P),
                new Cell(new Coords(1, 0), CellStatus.O),
                null
        };

        testGrid.insertCell(cells[0]);
        testGrid.insertCell(cells[1]);
        testGrid.insertCell(cells[2]);
        testGrid.insertCell(cells[3]);

        maze.discoverNeighborsInGrid(new Cell(new Coords(0, 5), CellStatus.O));

        assertNull((new Cell(new Coords(0, 5), CellStatus.O).getNeighbors()[0]));
        assertNull((new Cell(new Coords(0, 5), CellStatus.O).getNeighbors()[1]));
        assertNull((new Cell(new Coords(0, 5), CellStatus.O).getNeighbors()[2]));
        assertNull((new Cell(new Coords(0, 5), CellStatus.O).getNeighbors()[3]));

    }

}