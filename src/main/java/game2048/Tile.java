package game2048;

public class Tile {
    private int x;
    private int y;
    protected Integer value;

    public Tile(int x, int y, Integer value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
    
    public Tile() {
    }

    public Integer getValue() {
        return this.value; 
    }

    public void mergeWithTile(Tile newTile) {
        this.value = this.value + newTile.value;
    }

    public boolean canMerge(Tile newTile) {
        return this.value == newTile.value;
    }

}
