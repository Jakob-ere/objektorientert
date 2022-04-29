package game2048;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GameHandler implements IGameHandler {

    @Override
    public Grid readGrid(String filename, Grid grid) throws FileNotFoundException {
        try(Scanner scanner = new Scanner((getFile(filename)))) {
            grid.score = Integer.parseInt(scanner.nextLine());
            for(int i = 0; i < grid.rows.size(); i++) {
                String[] rad = scanner.nextLine().split(";");
                grid.rows.get(i).set(0, new Tile(i,0,Integer.parseInt(rad[0])));
                grid.rows.get(i).set(1, new Tile(i,1,Integer.parseInt(rad[1])));
                grid.rows.get(i).set(2, new Tile(i,2,Integer.parseInt(rad[2])));
                grid.rows.get(i).set(3, new Tile(i,3,Integer.parseInt(rad[3])));
            }
        }
        return grid;
    }

    @Override
    public void writeGrid(String filename, Grid grid) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(getFile(filename))) {
            writer.println(grid.score);
            for(int i = 0; i < grid.rows.size(); i++) {
                writer.println(grid.rows.get(i).get(0).getValue() + ";" + grid.rows.get(i).get(1).getValue() + ";" + grid.rows.get(i).get(2).getValue() + ";" + grid.rows.get(i).get(3).getValue());
            }
        }
    }
    
    public static File getFile(String filename) {
		return new File(filename + ".txt");
	}

}
