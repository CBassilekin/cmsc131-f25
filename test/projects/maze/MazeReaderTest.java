
package projects.Maze;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazeReaderTest {

    /**
     * this test checks that a maze can be loaded successfully from a valid file
     * we will use the same sample located at data/sample_maze_test2.txt
     */
    @Test
    public void testLoadIsSuccessFul() {

        Maze actualMaze = MazeReader.load("data/sample_maze_test2.txt");
        Maze expectedMaze = new Maze(4);

        assertEquals(expectedMaze.size(), actualMaze.size());
        assertNotNull(actualMaze);

    }

    /**
     * this test checks that the load() method throws an exception when a null file
     * is passed.
     */
    @Test
    public void testLoadthrowsOnNullput() {
        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    MazeReader.load(null);
                });
        assertEquals("file cannot be null, start over.", e.getMessage());
    }

    @Test
    public void testcountSpacesIn() {
        int expectedSpaceCount = 4;
        int actualSpaceCount = MazeReader.countSpacesIn("data/sample_maze_test2.txt");
        assertEquals(expectedSpaceCount, actualSpaceCount);
    }

    /**
     * this test checks that the countSpacesIn() method throws an exception when a
     * null file
     * is passed.
     */
    @Test
    public void testcountSpacesInthrowsOnNullput() {
        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    MazeReader.countSpacesIn(null);
                });
        assertEquals("file cannot be null, start over.", e.getMessage());
    }
}
