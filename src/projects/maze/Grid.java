package projects.maze;

public class Grid {

    private static int cellCount = 0;
    private static Cell[] cells;

    public Grid(int maxCells) {
        cells = new Cell[maxCells];

    }

    /**
     * method returns boolean after a cell is inserted.
     * 
     * @param cell - a container in a grid, defined by a coordinates and a status
     * @return
     *         - true if a cell is inserted sucessfully in the grid
     *         - false if a cell is not sucessfully inserted in the grid
     */
    public boolean insertCell(Cell cell) {
        if (cellCount < cells.length) {
            // bug? should be cellCount, then increment cellCount++;
            cells[cellCount] = cell;
            cellCount++;

            return true;
        }
        return false;
    }

    /**
     * method returns the cell object when coordinates are passed.
     * 
     * @param vh - coordinates
     * @return cell object of specific coordinates if exist or null otherwise;
     */
    public Cell getCell(Coords vh) {
        for (int idx = 0; idx < cellCount; idx++) {
            if (cells[idx].getCoords().equals(vh)) {
                return cells[idx];
            }
        }
        return null;
    }

    /**
     * method gives the count of cells.
     * 
     * @return cells count, as a integer.
     */
    public int getCellCount() {
        return cellCount;
    }

    /**
     * method returns an array of all cells successfully inserted.
     */
    public Cell[] getAllCells() {
        Cell[] allCells = new Cell[cellCount];
        for (int idx = 0; idx < cellCount; idx++) {
            allCells[idx] = cells[idx];
        }
        return allCells;
    }

}