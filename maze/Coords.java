package projects.maze;

public class Coords {

    private final int row;
    private final int col;

    public Coords(int r, int c) {
        row = r;
        col = c;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean equals(Coords other) {
        return (
            getRow() == other.getRow()
            && getCol() == other.getCol()
        );
    }

}
