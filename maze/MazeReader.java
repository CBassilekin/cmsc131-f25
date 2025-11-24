package projects.maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MazeReader {

    /**
     * Provided by Dusel. 
     * Assmes existence of CellStatus enum with values S, O, E.
     * Assumes existence of discoverAndSetupNeighbors instance method for Maze.
     * @param filename
     * @return
     */
    public static Maze load(String filename) {
        Scanner scanner;
        int spaceCount = countSpacesIn( filename );
        if (spaceCount == 0) {
            return null;
        }
        try {
            scanner = new Scanner(new File(filename));
            Maze maze = new Maze(spaceCount);   
            int row = 0, col = 0;
            while (scanner.hasNextLine()) {
                col = 0; // row begins with first column
                String[] tokens = scanner.nextLine().split(",");
                for (int i = 0; i < tokens.length; i++) {
                    if (!tokens[i].equals("X")) {
                        maze.insertCell(
                            new Cell(
                                new Coords(row, col),
                                CellStatus.valueOf(tokens[i]) 
                            )
                        );
                    }
                    col++;  // next space along same row
                }
                row++; // new row
            }
            scanner.close();
            maze.discoverAndSetupNeighbors();
            return maze;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int countSpacesIn( String filename ) {
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
