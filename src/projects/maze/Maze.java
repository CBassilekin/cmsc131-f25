/** Maze class specification

- `Maze(int maxCells)` - pre-allocates array to maximum size

- `getStart()` - finds and returns the cell with Status Start

- `getEnd()` - finds and returns the cell with Status End
    - Consider writing a helper method `getFirstCellWithStatus(Status)` which does linear search

- setupNeighbors() populates the neighbors list of each cell in the grid

 */

package projects.maze;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Maze {

    // attributes - grid will help build our maze.

    private Grid grid;
    private int size;

    // Maze constructor - maximum number of cells
    public Maze(int maxCells) {

        grid = new Grid(maxCells);
        size = maxCells;
    }

    /**
     * accessor method to return the size of a maze
     * 
     */
    public int size() {
        return size;
    }

    public boolean insertCell(Cell cell) {
        if (cell != null) {
            return grid.insertCell(cell);
        }
        throw new IllegalArgumentException("Parameter cell cannot be null.");
    }

    /**
     * method to setup neighbors for a given cell in the grid.
     * 
     * @param cell - a given cell in the grid.
     */
    public void setUpNeighbors(Cell cell) {

        if (cell == null) {
            throw new IllegalArgumentException(
                    "Parameter cell cannot be null");
        } else {
            int j = 0;

            // the potential neighbors of a cell in the order
            // East, West, South, North
            Coords[] maybeNeighbors = new Coords[] { new Coords(
                    cell.getCoords().getRow(),
                    cell.getCoords().getCol() + 1),
                    new Coords(cell.getCoords().getRow(),
                            cell.getCoords().getCol() - 1),
                    new Coords(cell.getCoords().getRow() + 1,
                            cell.getCoords().getCol()),
                    new Coords(cell.getCoords().getRow() - 1,
                            cell.getCoords().getCol()) };

            // building the final neighbors array
            // by elimitattins null neighbors and those not in the grid

            for (int i = 0; i < maybeNeighbors.length; i++) {
                Coords maybeNeighbor = maybeNeighbors[i];

                if (maybeNeighbor != null) {
                    // Search for matching cell in grid
                    for (Cell inGridCell : grid.getAllCells()) {
                        if (maybeNeighbor.equals(inGridCell.getCoords())) {
                            // Assign actual cell object
                            cell.neighbors[j] = maybeNeighbor;
                            j++;

                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * method returns a cell' status in the grid.
     * 
     * @param s - a given status can take these values
     *          - S, // maze entrance
     *          - E, // maze exit
     *          - O, // open cell
     *          - P, // part of solution path
     *          - X; // cell absent
     * 
     * @return the cell status.
     */
    public Cell getFirstCellWithStatus(CellStatus s) {
        if (s == null) {
            throw new IllegalArgumentException("this parameter cannot be empty.");
        } else {
            for (int i = 0; i < grid.getCellCount(); i++) {
                Cell lookUpCell = grid.getAllCells()[i];
                if (lookUpCell.getStatus() == s) {
                    return lookUpCell;
                }
            }
        }
        return null;
    }

    /**
     * method returns Entrance of the maze.
     * 
     * @param grid - an array of cells.
     * 
     * @return cell object with `Start` Status or null.
     */
    public Cell getStart() {
        return getFirstCellWithStatus(CellStatus.S);
    }

    /**
     * method find the exit of the grid.
     * 
     * @param grid - an array of cells with a given size.
     * 
     * @return the exit cell or null.
     */
    public Cell getEnd() {
        return getFirstCellWithStatus(CellStatus.E);
    }

    /**
     * Provided by Dusel. Assumes grid cell has a getStatus() method.
     * Method will write a grid of inserted cells to a file.
     * 
     * @param filename - Output filename.
     */
    public void serialize(String filename) {

        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null.");
        }

        Cell[] cells = grid.getAllCells();

        FileWriter writer;
        try {
            writer = new FileWriter(new File(filename));
            // discover dimension of maze's underlying grid
            int maxRow = 0, maxCol = 0;
            int idxCell;
            for (idxCell = 0; idxCell < cells.length; idxCell++) {
                int row = cells[idxCell].getCoords().getRow();
                int col = cells[idxCell].getCoords().getCol();
                if (row > maxRow) {
                    maxRow = row;
                }
                if (col > maxCol) {
                    maxCol = col;
                }
            }

            // write cell statuses, using 'X' for absent (inaccessible) cells
            idxCell = 0;
            for (int row = 0; row <= maxRow; row++) {
                for (int col = 0; col <= maxCol; col++) {
                    Cell maybeCell = grid.getCell(
                            new Coords(row, col));
                    if (maybeCell != null) {
                        writer.write(maybeCell.getStatus().name());
                        idxCell++;
                    } else {
                        writer.write('X');
                    }
                    writer.write(' ');
                }
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean dfs(Cell c) {

        // base case: mark the cell as explored
        c.setExplored(true);

        // we found the exit,
        if (c.getStatus() == CellStatus.E) {
            return true;
        }
        // mark the cell as part of the path tentatively
        if (c.getStatus() != CellStatus.S) {
            c.setStatus(CellStatus.P);
            return true;
        }

        // recursive call dfs on cell' neighbors
        setUpNeighbors(c);
        Coords[] neighborCoords = c.getNeighbors();

        // let us start traversing the neighbors
        for (int i = 0; i < neighborCoords.length; i++) {
            Cell lookUpCell = grid.getCell(neighborCoords[i]);

            // ignore null neighbors if any (getCell() may return null)
            if (lookUpCell == null) {
                continue;

            } // if the neighbor is unexplored, we dfs into it
            if (lookUpCell.isExplored()) {
                continue;
            }
            if (dfs(lookUpCell)) {
                return true;

            }
        }
        // Backtracking step: unmark cell as part of path
        if (c.getStatus() != CellStatus.S && c.getStatus() != CellStatus.E) {
            // change cell status back to O due to dead end;
            c.setStatus(CellStatus.O);

        }
        return false;

    }

    public boolean solveMaze() {
        Cell start = getStart();

        return dfs(start);
    }

}