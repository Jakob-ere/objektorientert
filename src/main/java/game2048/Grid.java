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
        randomNewNumber();
        randomNewNumber();
        randomNewNumber();
        randomNewNumber();
        randomNewNumber();
        System.out.println(this);
        System.out.println("-------");
        System.out.println("Move to Left:");
        moveToLeft();
        System.out.println(this);
        System.out.println("-------");
        System.out.println("Move to Right:");
        moveToRight();
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

    public void moveToRight() {
        ArrayList<ArrayList<Tile>> rader = this.rows;
        ArrayList<Tile> tomListe = new ArrayList<>();
        for (int y=0; y < 4; y++) {
            ArrayList<Tile> rad = horizontalMovePro(rader.get(y),y,0);
            rader.set(y, tomListe);
            rader.set(y, rad);
            for (int x=0; x < 4; x++) {
                rader.get(y).get(x).updateX(x);
            }
        }
    }

    public void moveToLeft() {
        ArrayList<ArrayList<Tile>> rader = this.rows;
        ArrayList<Tile> tomListe = new ArrayList<>();
        for (int y=0; y < 4; y++) {
            ArrayList<Tile> rad = horizontalMovePro(rader.get(y),y,1);
            rader.set(y, tomListe);
            rader.set(y, rad);
            for (int x=0; x < 4; x++) {
                rader.get(y).get(x).updateX(x);
            }
        }
    }
    
    public ArrayList<Tile> horizontalMovePro(ArrayList<Tile> rad, int rowNumb, int z) {
        if (z == 1) Collections.reverse(rad);
        rad.removeIf(Tile -> Tile.getValue().equals(0));
        if (rad.size() > 1) {
            for (int x = rad.size()-1; x > 0; x--) {
                rad.get(x).updateX(x);
                if (rad.get(x-1).canMerge(rad.get(x))){
                    rad = mergeWith(rad, x-1, x);
                    x -= 1;
                }
            }
        }
        rad = addTiles(rad, rowNumb);
        if (z == 1) Collections.reverse(rad);
        return rad;
    }

    public ArrayList<Tile> mergeWith(ArrayList<Tile> rad, int x1, int x2) {
        rad.get(x1).mergeWithTile(rad.get(x2));
        Collections.swap(rad, x1, x2);
        rad.remove(rad.get(x1));
        rad.get(x1).updateX(x1);
        return rad;
    }

    public ArrayList<Tile> addTiles(ArrayList<Tile> rad, int rowNumb) {
        if (rad.size() < 4) {
            for (int x=0; rad.size() < 4; x++) {
                rad.add(0, new Tile(rowNumb,x,0));
            }
        }
        return rad;

    }

    public boolean tileBiggerZero(Tile tile) {
        return tile.value > 0;
    }

    public ArrayList<ArrayList<Tile>> randomNewNumber() {
        ArrayList<Tile> emptySquares = new ArrayList<>();
        for (int y=0; y < 4; y++) {
            for (int x=0; x < 4; x++) {
                if (!tileBiggerZero(rows.get(y).get(x))) {
                    emptySquares.add(rows.get(y).get(x));
                } 
            }
        }
        System.out.println(printeMande(emptySquares));
        //if (emptySquares.size() == 0) hasLost();
        Random ran = new Random();
        int value = ran.nextInt(11);
        int newTile = ran.nextInt(emptySquares.size());
        int yCord = emptySquares.get(newTile).getY();
        int xCord = emptySquares.get(newTile).getX();
        if (value <= 7) value = 2;
        else value = 4;
        this.rows.get(yCord).set(xCord, new Tile(yCord,xCord,value));
        return this.rows;
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
                    utskrift += "[]";
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
