package projects.maze;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MazeTest {

    private int maxCells = 5;

    private Maze maze;

    @BeforeEach
    private void setUp() {
        maze = new Maze(maxCells);

        // we purposely did not pass a startCell here;
        // likewise for the endCell;
        // both will be added later.

        maze.insertCell(new Cell(new Coords(0, 0), CellStatus.O));
        maze.insertCell(new Cell(new Coords(0, 1), CellStatus.O));
        maze.insertCell(new Cell(new Coords(1, 1), CellStatus.P));
        maze.insertCell(new Cell(new Coords(1, 0), CellStatus.O));

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
     * new!
     * test confirms that cell insertion in the maze is reflected correctly
     * and that the inserted cell may be retrieved.
     */
    @Test
    public void testInsertCell() {
        Cell cellToInsert = new Cell(new Coords(2, 2), CellStatus.O);
        boolean insertResult = maze.insertCell(cellToInsert);
        assertTrue(insertResult);

        Cell retrievedCell = maze.getCell(new Coords(2, 2));
        assertNotNull(retrievedCell);
    }

    /**
     * new!
     * test confirms that inserting a null cell
     * throws an IllegalArgumentException.
     */
    @Test
    public void testInsertCellThrowsOnNullCell() {

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    maze.insertCell(null);
                });
        assertEquals(
                "Parameter cell cannot be null.",
                exception.getMessage());

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
    public void testSetUpNeighborReturnsCorrectArrayForStartCell() {
        Cell startCell = new Cell(new Coords(0, 2), CellStatus.S);
        maze.insertCell(startCell);
        maze.setUpNeighbors(maze.getStart());

        // Discovering actual Coords (0,0) neighbors
        // this test also serves for the Cell.getNeighbors () method.
        Coords[] actualNeighbors = (maze.getStart()).getNeighbors();

        // Estimating the expected result.
        Coords[] expectedNeighbors = new Coords[] { new Coords(0, 1), null, null, null };

        // expected vs actual should match, some neighbors are null.

        assertNotNull(actualNeighbors[0]);
        assertTrue(expectedNeighbors[0].equals(actualNeighbors[0]));

        assertNull(expectedNeighbors[1]);
        assertNull(actualNeighbors[1]);

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
    public void testSetUpNeighborReturnsEmptyArray() {
        // defining the lookUpCell, not present in the maze.
        Cell cell1 = new Cell(new Coords(1, 5), CellStatus.O);

        // Discovering Cell1 actual neighbors
        maze.setUpNeighbors(cell1);

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
     * new!
     * method tests that the maze's setUpNeighbors()
     * correctly throws an exception on null input.
     */
    @Test
    public void testSetUpNeighborsThrowsOnNullInput() {

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    maze.setUpNeighbors(null);
                });
        assertEquals(
                "Parameter cell cannot be null",
                exception.getMessage());

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
        maze.insertCell(new Cell(new Coords(2, 0), CellStatus.S));

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
        maze.insertCell(expectedCellwithStatutExit);

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
     * method checks that getStart() returns correct statCell if such cell exist.
     */
    @Test
    public void testgetStartReturnsCorrectCell() {
        maze.insertCell(new Cell(new Coords(2, 0), CellStatus.S));

        Cell expectedStart = new Cell(new Coords(2, 0), CellStatus.S);

        // using the getFirstCellWithStatus to confirm getStart() method
        maze.getFirstCellWithStatus(CellStatus.S);
        Cell actualStart = maze.getStart();
        assertTrue(expectedStart.getCoords().equals(actualStart.getCoords()));
        assertEquals(expectedStart.getStatus(), actualStart.getStatus());
        assertNotNull(actualStart);
    }

    /**
     * method checks that getStart() returns null if no such cell exist.
     */
    @Test
    public void testgetStartReturnsNull() {
        // no start cell inserted yet
        assertNull(maze.getStart());

    }

    /**
     * method check that the correct EndCell is returned by the getEnd() method.
     */
    @Test
    public void testgetEndReturnsCorrectEndCell() {
        maze.insertCell(new Cell(new Coords(4, 0), CellStatus.E));
        Cell actualEnd = maze.getEnd();
        assertNotNull(actualEnd);
        assertTrue(new Coords(4, 0).equals(actualEnd.getCoords()));
        assertEquals(CellStatus.E, actualEnd.getStatus());

    }

    /**
     * new!
     * method will throw an exception of a null grid is passed.
     */
    @Test
    public void testgetEndReturnsNullOnNullInput() {
        // no end cell inserted yet
        assertNull(maze.getEnd());

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
                assertEquals("O O", maze.getCell(new Coords(0, 0)).getStatus() + " "
                        + maze.getCell(new Coords(0, 1)).getStatus());
            } else if (lineNumber == 2) {
                // This assertion only runs when processing the second line
                assertEquals("O P", maze.getCell(new Coords(1, 0)).getStatus() + " "
                        + maze.getCell(new Coords(1, 1)).getStatus());

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

    /**
     * new and revised!
     * method test that the getAllCells() method is working correctly.
     */
    @Test
    void testGetAllCellsReturnsANotNullArray() {
        Cell[] allCells = maze.getAllCells();
        int expectedLength = maxCells - 1; // -1 because start cell not added yet
        int actualLength = allCells.length;

        // confirm that allCells is not null and has expected length
        assertNotNull(allCells);
        assertEquals(expectedLength, actualLength); // -1 because start cell not added yet

        // now compare cell statuses
        CellStatus[] expectedStatuses = {
                CellStatus.O,
                CellStatus.O,
                CellStatus.P,
                CellStatus.O
        };

        // let's compare the actual cells to expected cells 'explored' attribute.
        for (int idx = 0; idx < allCells.length; idx++) {
            Cell actualCell = allCells[idx];
            assertNotNull(actualCell);
            /*
             * compare actual status/explored values
             * to expected status/explored values
             */
            assertEquals(
                    expectedStatuses[idx],
                    actualCell.getStatus());
            assertFalse(actualCell.isExplored());
        }
        // let's compare the actual cells to expected cells coordinates.
        for (Cell cell : allCells) {
            Coords coords = cell.getCoords();
            assertNotNull(coords);
            assertTrue(
                    (coords.equals(new Coords(0, 0))) ||
                            (coords.equals(new Coords(0, 1))) ||
                            (coords.equals(new Coords(1, 0))) ||
                            (coords.equals(new Coords(1, 1))));

        }
    }

    /**
     * method test that getAllCells() method may return a null array correctly.
     */
    @Test
    void testGetAllCellsReturnsEmptyArrayWhenNoCells() {
        Maze emptyMaze = new Maze(3); // new maze with no cells added yet
        Cell[] allCells = emptyMaze.getAllCells();

        // confirm that allCells is not null and has expected length of 0
        assertNotNull(allCells);
        assertEquals(0, allCells.length); // expected length is 0
    }

    /**
     * new!
     * method test that getCell() works correctly
     * 
     */
    @Test
    void testGetCell() {
        // looking for an existing cell
        Cell cell = maze.getCell(new Coords(1, 1));
        assertNotNull(cell);
        assertEquals(CellStatus.P, cell.getStatus());
        assertEquals(1, cell.getCoords().getRow());
        assertEquals(1, cell.getCoords().getCol());

        // looking for a non-existing cell
        Cell nonExistentCell = maze.getCell(new Coords(3, 3));
        assertNull(nonExistentCell);
    }

    /**
     * new!
     * method test that getCellCount() works correctly
     */
    @Test
    void testGetCellCount() {
        // looking for the number of cells in the maze
        int cellCount = maze.getCellCount();
        assertEquals(4, cellCount); // 4 cells added in setUp()

        // looking for the number of cells in an empty maze
        Maze emptyMaze = new Maze(3);
        int emptyCellCount = emptyMaze.getCellCount();
        assertEquals(0, emptyCellCount); // no cells added yet
    }

    /**
     * method tests that the maze solving method is working correctly on trivial
     * mazes.
     */
    @Test
    void testSolveTrivial() {

        Maze exitOnly = new Maze(2); // S E
        exitOnly.insertCell(new Cell(new Coords(0, 0), CellStatus.S));
        exitOnly.insertCell(new Cell(new Coords(0, 1), CellStatus.E));

        // set up neighbors, then verify that exitOnly has a solution
        exitOnly.setUpNeighbors(exitOnly.getStart());
        Coords[] startCellNeighbors = exitOnly.getStart().getNeighbors();
        assertNotNull(startCellNeighbors[0]); // East neighbor should exist
        assertNull(startCellNeighbors[1]); // West neighbor should be null
        assertNull(startCellNeighbors[2]); // North neighbor should be null
        assertNull(startCellNeighbors[3]); // South neighbor should be null
        assertTrue(startCellNeighbors[0].equals(new Coords(0, 1)));
        assertEquals(1, (exitOnly.getStart()).getNeighborsCount());

        Boolean expectedResolution = exitOnly.solveMaze();
        assertNotNull(exitOnly);
        assertTrue(expectedResolution);

        Maze noExit = new Maze(2); // S O
        noExit.insertCell(new Cell(new Coords(0, 0), CellStatus.S));
        noExit.insertCell(new Cell(new Coords(0, 1), CellStatus.O));

        // set up neighbors, then verify that noExit has no solution
        noExit.setUpNeighbors(noExit.getStart());
        startCellNeighbors = noExit.getStart().getNeighbors();
        assertNotNull(startCellNeighbors[0]); // East neighbor should exist
        assertNull(startCellNeighbors[1]); // West neighbor should be null
        assertNull(startCellNeighbors[2]); // North neighbor should be null
        assertNull(startCellNeighbors[3]); // South neighbor should be null
        assertTrue(startCellNeighbors[0].equals(new Coords(0, 1)));
        assertEquals(1, (noExit.getStart()).getNeighborsCount());

        expectedResolution = noExit.solveMaze();
        assertNotNull(noExit);
        assertFalse(expectedResolution);
    }

    /*
     * method tests that the maze solving method is working correctly on a more
     * complex maze stored in test_maze.csv file.
     */
    @Test
    void testSolveTestMaze() {
        /*
         * load test_maze.csv into testMaze object, then verify that testMaze
         * has a solution
         */

        Maze testMaze = new Maze(5);
        testMaze = MazeReader.load("data/test_maze.csv");
        testMaze.solveMaze();
        Cell[] allCells = testMaze.getAllCells();

        // confirm that allCells is not null and has expected length
        assertNotNull(allCells);
        // confirm expected length is 5
        assertEquals(5, allCells.length);

        // now compare cell statuses
        CellStatus[] expectedStatuses = {
                CellStatus.S,
                CellStatus.P, CellStatus.P, CellStatus.E,
                CellStatus.O
        };

        for (int idx = 0; idx < allCells.length; idx++) {
            Cell actualCell = allCells[idx];
            assertNotNull(actualCell);
            /*
             * compare actual status/explored values
             * to expected status/explored values
             */

            // expected vs actual should match
            if (idx == 0 || idx == 3 || idx == 4) {

                assertEquals(
                        expectedStatuses[idx],
                        actualCell.getStatus());

                // cell [1] and cell [2] should be part of the solution path
            } else {
                assertEquals(
                        CellStatus.P,
                        actualCell.getStatus());
            }

        }
    }

}