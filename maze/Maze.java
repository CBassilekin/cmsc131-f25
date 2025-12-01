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
     * aceesor method to return the size of a maze
     * 
     */
    public int size() {
        return size;
    }

    /**
     * method setup neighbors coordinates of a cell
     * fill up the neighbors array for a given cell object
     * 
     */

    public void discoverAndSetupNeighbors(Cell cell) {
        Cell lookUpCell = cell;

        if (lookUpCell != null) {

            for (int i = 0; i < grid.getCellCount(); i++) {

                // we can't find this cell in the grid
                if (lookUpCell.getCoords().equals(grid.getAllCells()[i].getCoords())) {

                    // we can now find the cell in the grid.

                    // determining its four neighbors' coordinates.
                    lookUpCell.neighbors[0] = new Coords(lookUpCell.getCoords().getRow(),
                            lookUpCell.getCoords().getCol() + 1);

                    // Data validation for the first neighbor at row = 0.
                    if (lookUpCell.getCoords().getCol() > 0) {
                        lookUpCell.neighbors[1] = new Coords(lookUpCell.getCoords().getRow(),
                                lookUpCell.getCoords().getRow() - 1);
                    } else
                        lookUpCell.neighbors[1] = null;

                    lookUpCell.neighbors[3] = new Coords(lookUpCell.getCoords().getRow() + 1,
                            lookUpCell.getCoords().getCol());

                    // Data validation for the third neighbor at col = 0.
                    if (lookUpCell.getCoords().getRow() > 0) {
                        lookUpCell.neighbors[2] = new Coords(lookUpCell.getCoords().getRow() - 1,
                                lookUpCell.getCoords().getCol());
                    } else
                        lookUpCell.neighbors[2] = null;
                    break;
                }

            }
        } else {
            throw new IllegalArgumentException("Please enter a valid cell, cell cannot be null.");
        }
    }

    /**
     * method returns Entrance of the maze.
     * 
     * @param grid - an array of cells.
     * 
     * @return cell object with `Start` Status or null.
     */
    public Cell getStart(Grid grid) {
        for (int i = 0; i < grid.getCellCount(); i++) {
            Cell startCell = grid.getAllCells()[i];
            if (startCell.getStatus() == CellStatus.S) {
                return startCell;

            }
        }
        return null;
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
     * method find the exit of the grid.
     * 
     * @param grid - an array of cells with a given size.
     * 
     * @return the exit cell or null.
     */
    public Cell getEnd(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("grid cannot be empty.");
        } else {

            Cell endCell = getFirstCellWithStatus(CellStatus.E);
            if (endCell != null) {
                return endCell;
            }
        }
        return null;
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

}