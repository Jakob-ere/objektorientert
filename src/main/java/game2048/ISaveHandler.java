package game2048;

import java.io.FileNotFoundException;

public interface ISaveHandler {
    
    public Grid readGrid(String filename, Grid grid, boolean gameStillActive) throws FileNotFoundException;

    public void writeGrid(String filename, Grid grid, boolean gameStillActive) throws FileNotFoundException;
}
