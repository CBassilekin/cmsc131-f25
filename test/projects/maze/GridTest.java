package projects.Maze;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridTest {
    private Cell[] cells = new Cell[] { new Cell(new Coords(0, 0), CellStatus.E),
            new Cell(new Coords(1, 1), CellStatus.S) };
    private Grid grid;

    /*
     * Setting up our account to be used or not
     * with each test.
     * Amount is set for credit() and debit() methods
     */
    @BeforeEach
    void setupGrid() {
        grid = new Grid(2);
        grid.insertCell(cells[0]);
        grid.insertCell(cells[1]);
    }

    /**
     * Test method verifies that a cell is successfully inserted
     * in the grid and can be retrieved from it.
     */
    @Test
    public void testInsertAndRetrieveFirstCell() {

        Cell retrieved = grid.getCell(cells[0].getCoords());
        assertNotNull(retrieved);
    }

    /**
     * test method verofies that Cell Count is exact after cells are inserted
     * into the grid. We are inserting 2 cells into it for testing.
     * it also serves to test getCellCount().
     */
    @Test
    public void testCellCountAfterInsert() {

        assertEquals(2, grid.getCellCount());
    }

    /**
     * Test method confirm that "GetAllCells" returned the correct
     * number of cells inserted.
     * verifying also that each cell in the grid is non-null.
     */
    @Test
    public void testGetAllCellsReturnsCorrectCount() {

        cells = grid.getAllCells();
        assertEquals(2, cells.length);

    }

    /**
     * test method confirms that a cell can only be inserted
     * when capacity of the grid allows.
     * after full capacity is met, a cell can no longer be insertted
     * 
     * 
     */
    @Test
    public void testInsertAtCapacityBoundary() {

        assertFalse(grid.insertCell(new Cell(new Coords(3, 3), CellStatus.S)));

    }

    /**
     * test method verifies that getCell() returns the exact Coordinates and
     * status of a cell.
     */
    @Test
    public void getCellReturnsCorrectCell() {

        Cell expectedCell = grid.getCell(cells[0].getCoords());
        assertNotNull(expectedCell);
        assertTrue(cells[0].getCoords().equals(expectedCell.getCoords()));
        expectedCell = grid.getCell(cells[1].getCoords());
        assertNotNull(expectedCell);
        assertTrue(cells[1].getCoords().equals(expectedCell.getCoords()));

    }

    /**
     * test method verifies that if an unexisting cell is passed onto the getCell()
     * method,
     * null should be returned.
     */
    @Test
    public void getCellReturnsNull() {
        assertNull(grid.getCell(new Coords(2, 1)));

    }

    /**
     * test method verifies that getAllCells() returned the correct coordinates and
     * status of aN INSERTED cell.
     */
    @Test
    public void getAllCellsReturnsCorrectCells() {

        Cell[] expectedCells = grid.getAllCells();
        assertTrue(expectedCells[0].equals(grid.getCell(new Coords(0, 0))));
        assertTrue(expectedCells[1].equals(grid.getCell(new Coords(1, 1))));

        assertEquals(expectedCells[0].getStatus(), CellStatus.E);
        assertEquals(expectedCells[1].getStatus(), CellStatus.S);

    }

}