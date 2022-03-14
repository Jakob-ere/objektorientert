package game2048;

import javafx.scene.paint.Color;

public class Tile {
    protected int x;
    protected int y;
    protected Integer value;
    protected Color farge = new Color(0,0,0,1);

    public Tile(int y, int x, Integer value) {
        this.y = y;
        this.x = x;
        this.value = value;
    }
    
    public Tile(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value; 
    }

    public int getX() {
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }

    public void mergeWithTile(Tile newTile) {
        this.value = this.value + newTile.value;
    }

    public boolean canMerge(Tile newTile) {
        if (this.value == 0) return false;
        return this.value == newTile.value;
    }
    
    public void updateX(int x) {
        this.x = x;

    }

    public void updateY(int y) {
        this.y = y;
    }

    public String getTileColor() {
        String fargeOut = "";
        if (value == 0) {
            fargeOut = "#c8dcd2";
        }
        else if (value == 2) {
            fargeOut = "#eee4da";
        }
        else if (value == 4) {
            fargeOut = "ede0c8";
        }
        else if (value == 8) {
            fargeOut = "f2b179";
        }
        else if (value == 16) {
            fargeOut = "f59563";
        }
        else if (value == 32) {
            fargeOut = "f67c5f";
        }
        else if (value == 64) {
            fargeOut = "";
        }
        else if (value == 128) {
            farge = new Color(237, 207, 114, 1);
            fargeOut = "";
        }
        else if (value == 256) {
            farge = new Color(237, 204, 97, 1);
            fargeOut = "";
        }
        else if (value == 512) {
            farge = new Color(237, 200, 80, 1);
            fargeOut = "";
        }
        else if (value == 1024) {
            farge = new Color(237, 197, 63, 1);
            fargeOut = "";
        }
        else if (value == 2048) {
            farge = new Color(237, 194, 46, 1);
            fargeOut = "";
        }
        return fargeOut;
    }

    @Override
    public String toString() {
        return "Tile [value=" + value + ", y=" + y + ", x=" + x + "]";
    }
}
