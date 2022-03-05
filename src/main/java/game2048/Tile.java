package game2048;

public class Tile {
    protected int x;
    protected int y;
    protected Integer value;

    public Tile(int y, int x, Integer value) {
        this.y = y;
        this.x = x;
        this.value = value;
    }
    
    public Tile() {
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
        return this.value == newTile.value;
    }
    
    public void updateX(int x) {
        this.x = x;

    }

    public void updateY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Tile [value=" + value + ", y=" + y + ", x=" + x + "]";
    }
}
