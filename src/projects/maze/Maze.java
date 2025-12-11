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

    public Coords[] setNeighbors(Cell cell) {
        Coords[] maybeNeighbors = new Coords[4];

        if (grid.getCell(cell.getCoords()) == null) {
            return null;
        } else {

            // determining its four neighbors' coordinates
            // in this order EWSN.
            maybeNeighbors[0] = new Coords(
                    cell.getCoords().getRow(),
                    cell.getCoords().getCol() + 1);
            maybeNeighbors[1] = new Coords(cell.getCoords().getRow(),
                    cell.getCoords().getCol() - 1);
            maybeNeighbors[2] = new Coords(cell.getCoords().getRow() + 1,
                    cell.getCoords().getCol());
            maybeNeighbors[3] = new Coords(cell.getCoords().getRow() - 1,
                    cell.getCoords().getCol());

        }
        return maybeNeighbors;
    }

    /**
     * method setup neighbors coordinates of a cell
     * fill up the neighbors array for a given cell object
     * 
     */

    public void discoverNeighborsInGrid(Cell lookUpCell) {

        // Assuming: neighbors[0]=East, [1]=West, [2]=South, [3]=North

        Coords[] maybeNeighborsCoords = setNeighbors(lookUpCell);


            for (int i = 0; i < maybeNeighborsCoords.length; i++) {
                Coords maybeNeighbor = maybeNeighborsCoords[i];
             

                    // Search for matching cell in grid
                    for (Cell inGridCell : grid.getAllCells()) {
                        if (maybeNeighbor.equals(inGridCell.getCoords())) {
                            // Assign actual cell object
                            lookUpCell.neighbors[i] = maybeNeighbor;

                            // Found the neighbor for this direction
                            break;
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

    public boolean dfs(Cell c) {

        // mark the cell as explored
        c.setExplored();

        // get its neighbors [i]
        Coords [] neighborCoords = c.getNeighbors();

        /* Print out all the neighbors of this cell to the screen
        System.out.println ("East: " + (neighbors[0].getRow()+ "," +  neighbors[0].getCol()) + ","+
        "West:" + (neighbors[1].getRow()+ "," +  neighbors[1].getCol()) + " South: "+ (neighbors[2].getRow() + ","+ neighbors[2].getCol()) +","+ 
        "North: " + (neighbors[2].getRow() + ","+ neighbors[2].getCol()) );*/
        
        // repeat DFS on the neighbors
        for (int i = 0; i < neighborCoords.length; i ++){

            // let us start traversing the neighbors
            Cell lookUpCell = grid.getCell(neighborCoords[i]);

            if (lookUpCell == null) {
                continue;
            }

            //if the neighbor is not explored
            if(!lookUpCell.isExplored()){
            dfs (lookUpCell);            
            } 
        } return true;
    }

    public boolean DFSMaze() {
       Cell start = getStart(grid);
        
        return dfs(start);
    }

//for testing check test maze for status 'explored' and new CellStatus 'O --->P'
}