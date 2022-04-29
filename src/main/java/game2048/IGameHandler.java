package game2048;

import java.io.FileNotFoundException;

public interface IGameHandler {
    
    public Grid readGrid(String filename, Grid grid) throws FileNotFoundException;

    public void writeGrid(String filename, Grid grid) throws FileNotFoundException;
}
