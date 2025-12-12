package projects.maze;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GridTest {

    /**
     * new!
     * Test method verifies that a grid is successfully initialized
     * with zero cells.
     */
    @Test
    public void testGridInitialization() {
        Grid grid = new Grid(5);
        assertNotNull(grid);
        assertEquals(0, grid.getCellCount());
    }

    /**
     * Test method verifies that a cell is successfully inserted
     * in the grid and can be retrieved from it.
     */
    @Test
    public void testInsertAndRetrieveFirstCell() {

        Grid grid = new Grid(2);
        grid.insertCell(new Cell(new Coords(0, 0), CellStatus.E));
        grid.insertCell(new Cell(new Coords(1, 1), CellStatus.S));

        // is cell with Coords (0,0) retrievable?
        Cell retrieved = grid.getCell(new Coords(0, 0));
        assertNotNull(retrieved);
    }

    /**
     * test method verofies that Cell Count is exact after cells are inserted
     * into the grid. We are inserting 2 cells into it for testing.
     * it also serves to test getCellCount().
     */
    @Test
    public void testCellCountAfterInsert() {
        Cell[] cells = new Cell[] {
                new Cell(new Coords(0, 0), CellStatus.E),
                new Cell(new Coords(1, 1), CellStatus.S)
        };
        Grid grid = new Grid(2);
        grid.insertCell(cells[0]);
        grid.insertCell(cells[1]);

        // Also verifies getCellCount()
        assertEquals(2, grid.getCellCount());
    }

    /**
     * test method confirms that a cell can only be inserted
     * when capacity of the grid allows.
     * after full capacity is met, a cell can no longer be insertted
     * 
     * 
     */
    @Test
    public void testInsertAtCapacityBoundaryReturnsFalse() {
        Cell[] cells = new Cell[] {
                new Cell(new Coords(0, 0), CellStatus.E),
                new Cell(new Coords(1, 1), CellStatus.S)
        };
        Grid grid = new Grid(2);
        grid.insertCell(cells[0]);
        grid.insertCell(cells[1]);
        assertFalse(grid.insertCell(new Cell(new Coords(3, 3), CellStatus.S)));

    }

    /**
     * test method verifies that getCell() returns the exact Coordinates and
     * status of a cell.
     */
    @Test
    public void getCellReturnsCorrectCell() {
        Grid grid = new Grid(2);
        Cell[] cells = new Cell[] {
                new Cell(new Coords(0, 0), CellStatus.E),
                new Cell(new Coords(1, 1), CellStatus.S)
        };

        grid.insertCell(cells[0]);
        grid.insertCell(cells[1]);
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
        Cell[] cells = new Cell[] {
                new Cell(new Coords(0, 0), CellStatus.E),
                new Cell(new Coords(1, 1), CellStatus.S)
        };
        Grid grid = new Grid(2);
        grid.insertCell(cells[0]);
        grid.insertCell(cells[1]);

        // trying to get a cell that does not exist
        assertNull(grid.getCell(new Coords(2, 1)));

    }

    /**
     * Test method confirm that "GetAllCells" returned the correct
     * number of cells inserted.
     * verifying also that each cell in the grid is non-null.
     */
    @Test
    public void testGetAllCellsReturnsCorrectArraySize() {
        Cell[] cells = new Cell[] {
                new Cell(new Coords(0, 0), CellStatus.E),
                new Cell(new Coords(1, 1), CellStatus.S)
        };
        Grid grid = new Grid(2);
        grid.insertCell(cells[0]);
        grid.insertCell(cells[1]);
        cells = grid.getAllCells();
        assertEquals(2, cells.length);

    }

    /**
     * test method verifies that getAllCells() returned the correct coordinates and
     * status of aN INSERTED cell.
     */
    @Test
    public void getAllCellsReturnsCorrectCells() {
        Cell[] cells = new Cell[] {
                new Cell(new Coords(0, 0), CellStatus.E),
                new Cell(new Coords(1, 1), CellStatus.S)
        };
        Grid grid = new Grid(2);
        grid.insertCell(cells[0]);
        grid.insertCell(cells[1]);
        Cell[] expectedCells = grid.getAllCells();
        assertTrue(expectedCells[0].equals(grid.getCell(new Coords(0, 0))));
        assertTrue(expectedCells[1].equals(grid.getCell(new Coords(1, 1))));

        assertEquals(expectedCells[0].getStatus(), CellStatus.E);
        assertEquals(expectedCells[1].getStatus(), CellStatus.S);

    }

    /**
     * new!
     * test method verifies that getAllCells() return
     * null cells when the grid is empty.
     */
    @Test
    public void getAllCellsOnEmptyGridReturnsEmptyArray() {
        Grid grid = new Grid(5);
        Cell[] cells = grid.getAllCells();
        assertEquals(0, cells.length);
    }

}