package projects.maze;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MazeTest {

    private int maxCells = 5;
    private Cell[] cells = new Cell[5];
    private Grid testGrid = new Grid(maxCells);

    private Maze maze;

    @BeforeEach
    private void setUp() {
        maze = new Maze(maxCells);

        // we purposely did not pass a startCell here;
        // likewisw for the endCell;
        // both will be added later.
        cells = new Cell[] {
                new Cell(new Coords(0, 0), CellStatus.O),
                new Cell(new Coords(0, 1), CellStatus.O),
                new Cell(new Coords(1, 1), CellStatus.P),
                new Cell(new Coords(1, 0), CellStatus.O) };

        testGrid.insertCell(cells[0]);
        testGrid.insertCell(cells[1]);
        testGrid.insertCell(cells[2]);
        testGrid.insertCell(cells[3]);

    }

    /**
     * test constructor that confirm a maze
     * setup is correct. Maze is no longer empty.
     */
    @Test
    public void testConstructor() {

        assertNotNull(maze);
        // Also serve to test the size() method.
        assertEquals(5, maze.size());

    }

    /**
     * test method to confirm that coordinates of a cell neighbors are found
     * correctly and saved in an array in this order
     * Coords [0] - is the cell located East
     * Coords [1] - is the cell located West
     * Coords [2] - is the cell located North
     * Coords [3] - is the cell locatd South
     */
    @Test
    public void testDiscoverNeighborsInGridReturnsCorrectArrayForStartCell() {
        Cell startCell = new Cell(new Coords(0, 2), CellStatus.S);
        testGrid.insertCell(startCell);
        maze.discoverNeighborsInGrid(maze.getStart(testGrid));

        // Discovering actual Coords (0,0) neighbors
        // this test also serves for the Cell.getNeighbors () method.
        Coords[] actualNeighbors = (maze.getStart(testGrid)).getNeighbors();

        // Estimating the expected result.
        Coords[] expectedNeighbors = new Coords[] { null, new Coords(0, 1), null, null };

        // expected vs actual should match, some neighbors are null.

        assertNull(expectedNeighbors[0]);
        assertNull(actualNeighbors[0]);

        assertTrue(expectedNeighbors[1].equals(actualNeighbors[1]));
        assertNotNull(actualNeighbors[1]);

        assertNull(expectedNeighbors[2]);
        assertNull(actualNeighbors[2]);

        assertNull(actualNeighbors[3]);
        assertNull(expectedNeighbors[3]);

    }

    /**
     * test method should return an empty array when a Cell is not
     * inserted.
     */
    @Test
    public void testDiscoverandSetUpNeighborsReturnsEmptyArray() {
        // defining the lookUpCell, not present in the maze.
        Cell cell1 = new Cell(new Coords(1, 5), CellStatus.O);

        // Discovering Cell1 actual neighbors
        maze.discoverNeighborsInGrid(cell1);

        // getting the actual result
        Coords[] actualNeighbors = cell1.getNeighbors();

        // what result should be expected?
        Coords[] expectedNeighbors = new Coords[] { null, null, null, null };

        // needs to compare expected vs actual for matching purposes.
        assertNull(expectedNeighbors[0]);
        assertNull(actualNeighbors[0]);
        assertNull(expectedNeighbors[1]);
        assertNull(actualNeighbors[1]);
        assertNull(expectedNeighbors[2]);
        assertNull(actualNeighbors[2]);
        assertNull(expectedNeighbors[3]);
        assertNull(actualNeighbors[3]);
    }

    /**
     * method checks that getStart() returns correct statCell if such cell exist.
     */
    @Test
    public void testgetStartReturnsCorrectCell() {
        testGrid.insertCell(new Cell(new Coords(2, 0), CellStatus.S));

        Cell expectedStart = new Cell(new Coords(2, 0), CellStatus.S);
        Cell actualStart = maze.getStart(testGrid);
        assertTrue(expectedStart.getCoords().equals(actualStart.getCoords()));
        assertEquals(expectedStart.getStatus(), actualStart.getStatus());
        assertNotNull(actualStart);
    }

    /**
     * method checks that getStart() returns null if no such cell exist.
     */
    @Test
    public void testgetStartReturnsNull() {

        assertNull(maze.getStart(testGrid));

    }

    /**
     * method checks that getFirstCellWithStatusReturns Correct first cell it finds
     * with
     * a O = Open Status.
     */
    @Test
    public void testFirstCellWithStatusReturnsCorrectCellWithStatusOpen() {

        Cell expectedCell = new Cell(new Coords(0, 0), CellStatus.O);
        Cell actualFirstCellWithStatusOpen = maze.getFirstCellWithStatus(CellStatus.O);
        assertNotNull(actualFirstCellWithStatusOpen);
        assertTrue(expectedCell.getCoords().equals(actualFirstCellWithStatusOpen.getCoords()));

    }

    /**
     * method checks that getFirstCellWithStatusReturns Correct first cell it finds
     * with
     * a S = Start Status.
     */
    @Test
    public void testFirstCellWithStatusReturnsCorrectCellWithStatusStart() {
        testGrid.insertCell(new Cell(new Coords(2, 0), CellStatus.S));

        Cell expectedCellwithStatutStart = new Cell(new Coords(2, 0), CellStatus.S);
        Cell actualFirstCellWithStatusStart = maze.getFirstCellWithStatus(CellStatus.S);
        assertNotNull(actualFirstCellWithStatusStart);
        assertTrue(expectedCellwithStatutStart.getCoords().equals(actualFirstCellWithStatusStart.getCoords()));

    }

    /**
     * method checks that getFirstCellWithStatusReturns Correct first cell it finds
     * with
     * a E = Exit Status.
     */
    @Test
    public void testFirstCellWithStatusReturnsCorrectCellWithStatusExit() {

        Cell expectedCellwithStatutExit = new Cell(new Coords(3, 4), CellStatus.E);
        testGrid.insertCell(expectedCellwithStatutExit);

        Cell actualFirstCellWithStatusExit = maze.getFirstCellWithStatus(CellStatus.E);
        assertNotNull(actualFirstCellWithStatusExit);
        assertTrue(expectedCellwithStatutExit.getCoords().equals(actualFirstCellWithStatusExit.getCoords()));

    }

    /**
     * method checks that getFirstCellWithStatusReturns Correct first cell it finds
     * with
     * a P = Path Status.
     */
    @Test
    public void testFirstCellWithStatusReturnsCorrectCellWithStatusPath() {

        Cell expectedCellwithStatut = new Cell(new Coords(1, 1), CellStatus.P);

        Cell actualFirstCellWithStatus = maze.getFirstCellWithStatus(CellStatus.P);
        assertNotNull(actualFirstCellWithStatus);
        assertEquals(expectedCellwithStatut.getStatus(), actualFirstCellWithStatus.getStatus());

    }

    /**
     * method confirms that getFirstCellWithStatus() returns null on null argiment.
     */

    @Test
    public void testFirstCellWithStatusReturnsNullonNullArgument() {

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    maze.getFirstCellWithStatus(null);
                });
        assertEquals(
                "this parameter cannot be empty.",
                exception.getMessage());

    }

    /**
     * method check that the correct EndCell is returned by the getEnd() method.
     */
    @Test
    public void testgetEndReturnsCorrectEndCell() {
        testGrid.insertCell(new Cell(new Coords(4, 0), CellStatus.E));
        Cell actualEnd = maze.getEnd(testGrid);
        assertNotNull(actualEnd);
        assertTrue(new Coords(4, 0).equals(actualEnd.getCoords()));
        assertEquals(CellStatus.E, actualEnd.getStatus());

    }

    /**
     * method will throw an exception of a null grid is passed.
     */
    @Test
    public void testgetEndThrowsonNullInput() {

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    maze.getEnd(null);
                });
        assertEquals(
                "grid cannot be empty.",
                exception.getMessage());

    }

    /**
     * method will throw an exception of a null grid is passed.
     */
    @Test
    public void testgetEndReturnsNull() {

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    maze.getEnd(null);
                });
        assertEquals(
                "grid cannot be empty.",
                exception.getMessage());

    }

    /**
     * method checks that a copy of the array to a file is successful.
     */
    @Test
    public void testSerializeIsSuccessful() {
        int lineNumber = 0;

        // Act to serialize the grid
        maze.serialize("data/sample_maze_test1.txt");

        // Confirm method is working
        // Access content of the file
        try {

            Scanner scanner = new Scanner(new File("data/sample_maze_test1.txt"));
            String line = scanner.nextLine();
            lineNumber++; // Increment the counter after reading the line

            assertNotNull(line);
            if (lineNumber == 1) {
                // This assertion only runs when processing the first line
                assertEquals("O O", testGrid.getCell(new Coords(0, 0)).getStatus() + " "
                        + testGrid.getCell(new Coords(0, 1)).getStatus());
            } else if (lineNumber == 2) {
                // This assertion only runs when processing the second line
                assertEquals("O P", testGrid.getCell(new Coords(1, 0)).getStatus() + " "
                        + testGrid.getCell(new Coords(1, 1)).getStatus());

            }
            scanner.close();

        } catch (

        FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testSerializeThrowsOnNullInput() {

        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    maze.serialize(null);
                });
        assertEquals("Filename cannot be null.", e.getMessage());
    }

}