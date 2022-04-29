package game2048;

public class Tile {
    private int x;
    private int y;
    private Integer value;

    public Tile(int y, int x, Integer value) {
        if (value < 0) throw new IllegalArgumentException("A tile must be positive.");
        if (y < 0 || y > 3) throw new IllegalArgumentException("The coordinates must be between 0 and 3");
        if (x < 0 || x > 3) throw new IllegalArgumentException("The coordinates must be between 0 and 3");
        this.y = y;
        this.x = x;
        this.value = value;
    }
    
    public Tile(Integer value) {
        if (value < 0) throw new IllegalArgumentException("A tile must be positive.");
        this.value = value;
    }

    public Integer getValue() {
        return this.value; 
    }

    public int getRow() {
        return this.y;
    }
    
    public int getColumn(){
        return this.x;
    }

    //adding this.value with the same value
    public void mergeWithTile(Tile newTile) {
        this.value = this.value + newTile.value;
    }

    //checks if the two tiles can be merged
    public boolean canMerge(Tile newTile) {
        if (this.value == 0) return false;
        return this.value.equals(newTile.value);
    }

    public boolean greaterThanZero() {
        return this.value > 0;
    }
    
    public void setColumn(int x) {
        this.x = x;

    }

    public void setRow(int y) {
        this.y = y;
    }

    //gets the tileColor depending on what the value is
    public String getTileColor() {
        String fargeOut = "";
        if (value == 0) {
            fargeOut = "#c8dcd2";
        }
        else if (value == 2) {
            fargeOut = "#eee4da";
        }
        else if (value == 4) {
            fargeOut = "#ede0c8";
        }
        else if (value == 8) {
            fargeOut = "#f2b179";
        }
        else if (value == 16) {
            fargeOut = "#f59563";
        }
        else if (value == 32) {
            fargeOut = "#f67c5f";
        }
        else if (value == 64) {
            fargeOut = "#f65e3b";
        }
        else if (value == 128) {
            fargeOut = "#f9dd53";
        }
        else if (value == 256) {
            fargeOut = "#85f953";
        }
        else if (value == 512) {
            fargeOut = "#00d15e";
        }
        else if (value == 1024) {
            fargeOut = "#00cad1";
        }
        else if (value == 2048) {
            fargeOut = "#6163db";
        }
        else if (value == 4096) {
            fargeOut = "#edc20f";
        }
        return fargeOut;
    }

    @Override
    public boolean equals(Object object) {
        if (this.getValue().equals(((Tile) object).getValue())) return true;
        return false;
    }

    @Override
    public String toString() {
        return "[" + value +"]";
    }
}
