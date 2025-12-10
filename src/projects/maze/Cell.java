/** `Cell` class additions

Extra attributes

- `neighbors` (Coords[]) - coordinates of neighbors
    - needs accessor/mutator

- `explored` (boolean) - traversal flag (defaults to false)
    - needs accessor/mutator

- `status` (CellStatus enum) - cell's role/state
    - Enum values    
        - `S` maze entrance
        - `E` maze exit  
        - `O` open cell
        - `P` part of solution path
    - Needs accessor/mutator

A cell will be constructed with a coordinates and a status. Decide for yourself what are sensible default values (if any) for the other attributes.
 */

package projects.maze;

public class Cell {

    private final Coords coords;

    // YB added the cell' extra attributes: 'status', 'explored', neighborsCount'
    // and 'neighbors'
    private final CellStatus status;
    private boolean explored = false;
    public Coords[] neighbors = new Coords[4];

    // YB added the cellStatus as part of the constructor
    public Cell(Coords c, CellStatus cs) {
        if ((c == null) || (cs == null)) {

            throw new IllegalArgumentException("this parameter cannot be empty.");
        }

        coords = c;
        status = cs;

    }

    // this getter method returns the Cell status
    public Coords getCoords() {
        return coords;
    }

    // this getter method returns the Cell status
    public CellStatus getStatus() {
        return status;

    }

    // this method returns the explored status of a cell
    public boolean isExplored() {
        return explored;
    }

    public boolean setExplored(){
        return true;
    }

    // YB added this method to help return an array of all neighbors of a cell
    public Coords[] getNeighbors() {
        // this method is called in the Maze class.
        return neighbors;
    }

}