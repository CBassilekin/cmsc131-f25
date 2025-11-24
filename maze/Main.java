package projects.maze;

public class Main {

    static void phase1() {
        Maze maze = MazeReader.load( "data/sample_maze.txt" );
        System.out.println("Maze successfully loaded!");
        maze.serialize("data/sample_maze_out.txt");
    }

    public static void main(String[] args) {
        phase1();
    }

}
