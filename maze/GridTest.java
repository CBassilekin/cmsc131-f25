package projects.maze;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GridTest {

    @Test
    public void testInsertAndRetrieveFirstCell() {
        Grid grid = new Grid(10);
        Coords coords = new Coords(0, 0);
        Cell cell = new Cell(coords);
        
        grid.insertCell(cell);
        
        Cell retrieved = grid.getCell(coords);
        assertNotNull(retrieved);
    }

    @Test
    public void testCellCountAfterInsert() {
    Grid grid = new Grid(10);
    Cell cell1 = new Cell(new Coords(0, 0));
    Cell cell2 = new Cell(new Coords(1, 1));
    
    grid.insertCell(cell1);
    assertEquals(1, grid.getCellCount());
    
    grid.insertCell(cell2);
    assertEquals(2, grid.getCellCount());
}

@Test
public void testGetAllCellsReturnsCorrectCount() {
    Grid grid = new Grid(10);
    grid.insertCell(new Cell(new Coords(0, 0)));
    grid.insertCell(new Cell(new Coords(1, 1)));
    grid.insertCell(new Cell(new Coords(2, 2)));
    
    Cell[] allCells = grid.getAllCells();
    assertEquals(3, allCells.length);
    
    for (int i = 0; i < allCells.length; i++) {
        assertNotNull(allCells[i]);
    }
}

@Test
public void testInsertAtCapacityBoundary() {
    Grid grid = new Grid(3);
    
    assertTrue(grid.insertCell(new Cell(new Coords(0, 0))));
    assertTrue(grid.insertCell(new Cell(new Coords(1, 1))));
    assertTrue(grid.insertCell(new Cell(new Coords(2, 2))));
    assertFalse(grid.insertCell(new Cell(new Coords(3, 3))));
}

}
