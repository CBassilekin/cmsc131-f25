package projects.maze;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class CellTest {

    private Cell testCell;

    @Test
    public void testConstructor() {
        Coords expectedCoords = new Coords(1, 1);
        CellStatus expectedStatus = CellStatus.S;
        testCell = new Cell(expectedCoords, expectedStatus);
        assertNotNull(testCell);

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
        testCell = new Cell(new Coords(0, 0), CellStatus.O);
        Coords expectedCoords = new Coords(0, 0);

        assertTrue(testCell.getCoords().equals(expectedCoords));
    }

    /**
     * test method to verfies correct Status is returned
     */
    @Test
    public void getStatusReturnsCorrectValue() {
        testCell = new Cell(new Coords(0, 0), CellStatus.O);
        CellStatus expectedStatus = CellStatus.O;

        assertEquals(expectedStatus, testCell.getStatus());
    }

    /**
     * test method to verfies traversal Flag returns false.
     */
    @Test
    public void testIsExploredReturnsFalseByDefault() {
        testCell = new Cell(new Coords(0, 0), CellStatus.S);
        assertFalse(testCell.isExplored());
    }

    /**
     * new!!
     * test methid to verfies traversal Flag is updated correctly.
     */
    @Test
    public void testSetExploredUpdatesFlagCorrectly() {
        testCell = new Cell(new Coords(0, 0), CellStatus.S);
        testCell.setExplored(true);
        assertTrue(testCell.isExplored());
    }

    /**
     * new!!
     * test method to verfies cell status is updated correctly.
     * 'O' to 'P' or 'P' to 'O'
     */
    @Test
    public void testSetStatusUpdatesStatusCorrectly() {
        // changing from 'O' to 'P'
        testCell = new Cell(new Coords(0, 0), CellStatus.O);
        testCell.setStatus(CellStatus.P);
        assertEquals(CellStatus.P, testCell.getStatus());

        // changing from 'P' to 'O'
        testCell = new Cell(new Coords(0, 0), CellStatus.P);
        testCell.setStatus(CellStatus.O);
        assertEquals(CellStatus.O, testCell.getStatus());
    }

    /**
     * test method checks that neighbors to a cell in the grid are accurately
     * returned
     * by the getNeighbors() method.
     */
    @Test
    public void getNeighborsReturnsCorrectArray() {

        // Setting up a 4x4 maze grid
        Maze maze = new Maze(4);

        maze.insertCell(new Cell(new Coords(0, 0), CellStatus.O));
        maze.insertCell(new Cell(new Coords(0, 1), CellStatus.O));
        maze.insertCell(new Cell(new Coords(1, 1), CellStatus.P));
        maze.insertCell(new Cell(new Coords(1, 0), CellStatus.O));

        // Finding the expected neighbors of cell (new Coorrds (0,0), CellStatus.O)
        Coords[] expectedNeighbors = new Coords[] { new Coords(0, 1), new Coords(1, 0), null, null };

        // Finding the actual neighbors from the getNeighbors() method
        Cell lookUpCell = (maze.getAllCells()[0]);
        maze.setUpNeighbors(lookUpCell);
        Coords[] actualNeighbors = (lookUpCell.getNeighbors());

        assertNotNull(expectedNeighbors);
        assertNotNull(actualNeighbors);
        assertTrue(expectedNeighbors[0].equals(actualNeighbors[0]));
        assertTrue(expectedNeighbors[1].equals(actualNeighbors[1]));

        assertNull(actualNeighbors[2]);
        assertNull(actualNeighbors[3]);

    }

    /**
     * 
     * test method should return an empty array when a Cell not
     * present in the grid is passed onto the getNeighbors() method.
     * Also if a cell with no neighbors in the grid is passed onto it.
     */
    @Test
    public void getNeighborsReturnsEmptytArray() {

        Maze maze = new Maze(5);

        maze.insertCell(new Cell(new Coords(0, 0), CellStatus.O));
        maze.insertCell(new Cell(new Coords(0, 1), CellStatus.O));
        maze.insertCell(new Cell(new Coords(1, 1), CellStatus.P));
        maze.insertCell(new Cell(new Coords(1, 10), CellStatus.O));

        // Trying to get neighbors for a cell not in the grid
        maze.setUpNeighbors(new Cell(new Coords(0, 5), CellStatus.O));

        assertNull((new Cell(new Coords(0, 5), CellStatus.O).getNeighbors()[0]));
        assertNull((new Cell(new Coords(0, 5), CellStatus.O).getNeighbors()[1]));
        assertNull((new Cell(new Coords(0, 5), CellStatus.O).getNeighbors()[2]));
        assertNull((new Cell(new Coords(0, 5), CellStatus.O).getNeighbors()[3]));

        // new!Trying a cell with no neighbors in the grid
        maze.setUpNeighbors(new Cell(new Coords(0, 10), CellStatus.O));
        assertNull((new Cell(new Coords(0, 10), CellStatus.O).getNeighbors()[0]));
        assertNull((new Cell(new Coords(0, 10), CellStatus.O).getNeighbors()[1]));
        assertNull((new Cell(new Coords(0, 10), CellStatus.O).getNeighbors()[2]));
        assertNull((new Cell(new Coords(0, 10), CellStatus.O).getNeighbors()[3]));

    }

    /**
     * new!
     * test method checks that getNeighborsCount() method returns the correct
     * number of neighbors for a given cell.
     */
    @Test
    public void getNeighborsCountReturnsCorrectValue() {
        Maze maze = new Maze(6);

        maze.insertCell(new Cell(new Coords(0, 0), CellStatus.O));
        maze.insertCell(new Cell(new Coords(0, 1), CellStatus.O));
        maze.insertCell(new Cell(new Coords(1, 1), CellStatus.P));
        maze.insertCell(new Cell(new Coords(1, 0), CellStatus.O));
        maze.insertCell(new Cell(new Coords(0, 2), CellStatus.O));
        maze.insertCell(new Cell(new Coords(2, 5), CellStatus.O));

        // Finding the actual neighbors count from the getNeighborsCount() method
        // choosing cell at (0,0) which has 2 neighbors
        Cell lookUpCell = (maze.getAllCells()[0]);
        maze.setUpNeighbors(lookUpCell);
        int actualNeighborsCount = (lookUpCell.getNeighborsCount());
        assertEquals(2, actualNeighborsCount);

        // Now testing a cell with only one neighbor
        lookUpCell = (maze.getAllCells()[4]); // cell at (0,2)
        maze.setUpNeighbors(lookUpCell);
        actualNeighborsCount = (lookUpCell.getNeighborsCount());
        assertEquals(1, actualNeighborsCount);

        // Now testing a cell with no neighbors
        lookUpCell = (maze.getAllCells()[5]); // cell at (2,5)
        maze.setUpNeighbors(lookUpCell);
        actualNeighborsCount = (lookUpCell.getNeighborsCount());
        assertEquals(0, actualNeighborsCount);

        // Now testing a cell not in the grid
        lookUpCell = new Cell(new Coords(5, 5), CellStatus.O); // cell at (5,5)
        maze.setUpNeighbors(lookUpCell);
        actualNeighborsCount = (lookUpCell.getNeighborsCount());
        assertEquals(0, actualNeighborsCount);

    }
}