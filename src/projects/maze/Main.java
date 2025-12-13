package projects.maze;

public class Main {

    static void phase1() {
        Maze maze = MazeReader.load("data/sample_maze.txt");
        System.out.println("Maze successfully loaded!");
        maze.serialize("data/sample_maze_out.txt");
    }

    static void phase2() {
        Maze maze = MazeReader.load("data/sample_maze.txt");
        maze.solveMaze();
        System.out.println("Maze Solved!");
    }

    static void hardMazes() {
        Maze maze = MazeReader.load("data/hard_maze.txt");
        if (maze.solveMaze()) {
            maze.serialize("data/hard_maze.out");
        }
        maze = MazeReader.load("data/hard_maze_nosol.txt");
        if (maze.solveMaze()) {
            maze.serialize("data/hard_maze_nosol.out");
        }
    }

    public static void main(String[] args) {
        phase1();
        phase2();
        hardMazes();
    }

}
