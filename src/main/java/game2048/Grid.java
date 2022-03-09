package game2048;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Grid {
    ArrayList<ArrayList<Tile>> rows = new ArrayList<>();

    public Grid() {
        makeGrid();
        randomNewNumber();
        randomNewNumber();
        randomNewNumber();
        randomNewNumber();
        randomNewNumber();
        System.out.println(this);
        randomNewNumber();
        randomNewNumber();
        randomNewNumber();
        randomNewNumber();
        randomNewNumber();
        randomNewNumber();
        System.out.println(this);
        System.out.println("-------");
        System.out.println("Move up:");
        moveUp();
        System.out.println(this);
       
    }

    public void makeGrid() {
        for (int y=0; y < 4; y++) {
            ArrayList<Tile> row = new ArrayList<Tile>();
            for (int x=0; x<4; x++) {
                row.add(new Tile(y,x,0));
            }
            this.rows.add(row);
        }
    }

    public void moveUp() {
        rotateGrid("right");
        moveHorizontal("right");
        rotateGrid("left");
        updateCordinates();
        randomNewNumber();
    } 

    public void moveDown() {
        rotateGrid("left");
        moveHorizontal("right");
        rotateGrid("rigth");
        updateCordinates();
        randomNewNumber();
    }

    public void moveToRight() {
        moveHorizontal("right");
        updateCordinates();
        randomNewNumber();
    }

    public void moveToLeft() {
        moveHorizontal("left");
        updateCordinates();
        randomNewNumber();
    }

    public void rotateGrid(String direction) {
        final int M = rows.size();
        ArrayList<ArrayList<Tile>> nyRows = new ArrayList<>();
        for (int r = 0; r < 4; r++) {
            ArrayList<Tile> rad = addTiles(new ArrayList<Tile>());
            nyRows.add(rad);
        }
        for (int r = 0; r < M; r++) {
            int N = rows.get(r).size();
            for (int c = 0; c < N; c++) {
                if (direction.equals("left")) nyRows.get(M-1-c).set(r, rows.get(r).get(c));  
                else nyRows.get(c).set(M-1-r, rows.get(r).get(c));
            }
        }
        this.rows = nyRows;
    }
    

    public void moveHorizontal(String direction) {
        int z = 0;
        if (direction.equals("left")) z = 1;
        ArrayList<ArrayList<Tile>> rader = this.rows;
        ArrayList<Tile> tomListe = new ArrayList<>();
        for (int y=0; y < 4; y++) {
            ArrayList<Tile> rad = horizontalMovePro(rader.get(y),z);
            rader.set(y, tomListe);
            rader.set(y, rad);
        }
    }

    
    public ArrayList<Tile> horizontalMovePro(ArrayList<Tile> rad, int z) {
        if (z == 1) Collections.reverse(rad);
        rad.removeIf(Tile -> Tile.getValue().equals(0));
        if (rad.size() > 1) {
            mergeRow(rad);
        }
        rad = addTiles(rad);
        if (z == 1) Collections.reverse(rad);
        return rad;
    }

    public ArrayList<Tile> mergeRow(ArrayList<Tile> rad) {
        for (int x = rad.size()-1; x > 0; x--) {
            rad.get(x).updateX(x);
            if (rad.get(x-1).canMerge(rad.get(x))){
                rad = tileMergeWithTile(rad, x-1, x);
                x -= 1;
            }
        }
        return rad;
    }
    public ArrayList<Tile> tileMergeWithTile(ArrayList<Tile> rad, int x1, int x2) {
        rad.get(x1).mergeWithTile(rad.get(x2));
        Collections.swap(rad, x1, x2);
        rad.remove(rad.get(x1));
        return rad;
    }

    public  void updateCordinates() {
        for (int r = 0; r < rows.size(); r++) {
            for (int c = 0; c < rows.get(r).size(); c++) {
                rows.get(r).get(c).updateX(c);
                rows.get(r).get(c).updateY(r);
            }
        }
    }
    public ArrayList<Tile> addTiles(ArrayList<Tile> rad) {
        if (rad.size() < 4) {
            for (int x=0; rad.size() < 4; x++) {
                rad.add(0, new Tile(0));
            }
        }
        return rad;
    }

    public boolean tileBiggerZero(Tile tile) {
        return tile.value > 0;
    }

    public ArrayList<Tile> emptyList() {
        ArrayList<Tile> emptySquares = new ArrayList<>();
        for (int y=0; y < 4; y++) {
            for (int x=0; x < 4; x++) {
                if (!tileBiggerZero(rows.get(y).get(x))) {
                    emptySquares.add(rows.get(y).get(x));
                } 
            }
        }
        return emptySquares;
    }

    public ArrayList<ArrayList<Tile>> randomNewNumber() {
        ArrayList<Tile> emptySquares = emptyList();
        Random ran = new Random();
        int value = ran.nextInt(11);
        int newTile = ran.nextInt(emptySquares.size());
        int yCord = emptySquares.get(newTile).getY();
        int xCord = emptySquares.get(newTile).getX();
        rows.get(yCord).set(xCord, new Tile(yCord,xCord,value >= 7 ? 4 : 2));
        return rows;
    }

    @Override
    public String toString() {
        String utskrift = ""; 
        for (int y=0; y < rows.size(); y++) {
            utskrift += "[";
            for (int x=0; x < 4; x++) {
                if (tileBiggerZero(this.rows.get(y).get(x))) {
                    utskrift += "["+this.rows.get(y).get(x).getValue()+"]";
                }
                else {
                    utskrift += "[] ";
                }
            }
            utskrift += "]\n";
        }
        return utskrift;
    }
    
    public String printeMande(ArrayList<Tile> lista) {
        String utskrift = ""; 
        for (int x=0; x < lista.size(); x++) {
            if (tileBiggerZero(lista.get(x))) {
                utskrift += "["+lista.get(x).getValue()+"]";
            }
            else {
                utskrift += "[]";
            }
        }
        return utskrift;
    }

    public static void main(String[] args) {
        Grid grid = new Grid();
        System.out.println(grid.rows.get(0).get(0));
    }
}
