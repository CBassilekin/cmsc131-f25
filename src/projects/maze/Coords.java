package projects.maze;

/**
 * `Coords class additions
 * 
 * attributes
 * 
 * - `row` (int) - row coordinates of cell
 * - needs accessor/mutator
 * 
 * - `col` (int) - row coordinates of cell
 * - needs accessor/mutator
 */

public class Coords {

    private final int row;
    private final int col;

    /**
     * Method defines constructor
     * r - is any given row number going south down on the grid; it should be an
     * integer.
     * c - is any given column number couting Eastward on the grid;
     */
    public Coords(int r, int c) {
        // assign given row and col to attributes
        // YB removed validation to allow negative coords
        // for setUpNeighbors().
        // it is also assumed that the maze file will
        // always provide positive values.
        row = r;
        col = c;
    }

    /**
     * Method returns the row number
     **/
    public int getRow() {
        return row;
    }

    /**
     * Method returns the column number
     **/
    public int getCol() {
        return col;
    }

    /**
     * this method checks given coordinates
     * matches against existing ones.
     * 
     * @param other - coordinates of a cell which has a row and col
     * @return true if coordinates match.
     * 
     */
    public boolean equals(Coords other) {
        if (other != null) {
            return (getRow() == other.getRow()
                    && getCol() == other.getCol());
        }

        return false;
    }

}