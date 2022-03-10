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

    public void setTileColor() {
        if (value == 2) {
            farge = new Color(238, 228, 218, 1);
        }
        else if (value == 4) {
            farge = new Color(237, 224, 200, 1);
        }
        else if (value == 8) {
            farge = new Color(242, 177, 121, 1);
        }
        else if (value == 16) {
            farge = new Color(245, 149, 99, 1);
        }
        else if (value == 32) {
            farge = new Color(246, 124, 95, 1);
        }
        else if (value == 64) {
            farge = new Color(246, 94, 59, 1);
        }
        else if (value == 128) {
            farge = new Color(237, 207, 114, 1);
        }
        else if (value == 256) {
            farge = new Color(237, 204, 97, 1);
        }
        else if (value == 512) {
            farge = new Color(237, 200, 80, 1);
        }
        else if (value == 1024) {
            farge = new Color(237, 197, 63, 1);
        }
        else if (value == 2048) {
            farge = new Color(237, 194, 46, 1);
        }
    }

    @Override
    public String toString() {
        return "Tile [value=" + value + ", y=" + y + ", x=" + x + "]";
    }
}
