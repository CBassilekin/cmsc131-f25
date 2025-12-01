package projects.maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MazeReader {

    /**
     * Provided by Dusel.
     * Assumes existence of CellStatus enum with values S, O, E.
     * Assumes existence of discoverAndSetupNeighbors instance method for Maze.
     * YB added missing methods to make the method work successfully.
     * 
     * @param filename - text file
     * @return Maze type object made of cells with coordinates, status and
     *         neighbors.
     */
    public static Maze load(String filename) {

        // YB added the exception in case a null file might be passed.
        if (filename == null) {
            throw new IllegalArgumentException("file cannot be null, start over.");
        }
        Cell insertCell = null;
        Scanner scanner;
        int spaceCount = countSpacesIn(filename);
        if (spaceCount == 0) {
            return null;
        }
        try {
            scanner = new Scanner(new File(filename));
            Maze maze = new Maze(spaceCount);
            Grid grid = new Grid(spaceCount);

            int row = 0, col = 0;
            while (scanner.hasNextLine()) {
                col = 0; // row begins with first column
                String[] tokens = scanner.nextLine().split(",");
                for (int i = 0; i < tokens.length; i++) {
                    if (!tokens[i].equals("X")) {
                        // YB changes 'maze.insertCell' to 'Grid.insertCell'
                        insertCell = new Cell(
                                new Coords(row, col),
                                CellStatus.valueOf(tokens[i]));
                        grid.insertCell(insertCell);

                    }
                    col++; // next space along same row
                }
                row++; // new row
            }
            scanner.close();
            maze.discoverAndSetupNeighbors(insertCell);
            return maze;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // YB added pulic to this metho type to allow access
    /**
     * this method calculate the cell Count of a grid reading from a file.
     * 
     * @param filename - filename is a.ssumed to be a correct file
     *                 with limited to no mistakes.
     * 
     * @return int SpacesCount - the number of Cells with a valid status from a
     *         file.
     * 
     */
    public static int countSpacesIn(String filename) {

        // YB added the exception in case a null file might be passed.
        if (filename == null) {
            throw new IllegalArgumentException("file cannot be null, start over.");
        }
        try {
            Scanner scanner = new Scanner(new File(filename));
            int spaceCount = 0;
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(",");
                for (int i = 0; i < tokens.length; i++) {
                    if (!tokens[i].equals("X")) {
                        spaceCount++;
                    }
                }
            }
            scanner.close();
            return spaceCount;
        } catch (java.io.FileNotFoundException ex) {
            return 0;
        }
    }

}