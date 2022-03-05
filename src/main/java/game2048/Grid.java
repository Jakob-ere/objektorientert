package game2048;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Grid {
    ArrayList<ArrayList<Tile>> rows = new ArrayList<>();

    public Grid() {
        makeGrid();
        System.out.println(this);
        //randomNewNumber();
        //moveToRight();
        System.out.println(this);
        moveToLeft();
        System.out.println(this);
       

    }

    public void makeGrid() {
        for (int x=0; x < 4; x++) {
            ArrayList<Tile> row = new ArrayList<Tile>();
            for (int y=0; y<4; y++) {
                if (y == 2) row.add(new Tile(x,y,4));
                else if (y == 3) row.add(new Tile(x,y,0));
                else if (y == 1) row.add(new Tile(x,y,0));
                else if (y == 0) row.add(new Tile(x,y,4));
                else {
                    row.add(new Tile(x,y,0));
                }
            }
            this.rows.add(row);
        }
    }

    public void moveToRight() {
        ArrayList<ArrayList<Tile>> rader = this.rows;
        ArrayList<Tile> tomListe = new ArrayList<>();
        for (int x=0; x < 4; x++) {
            ArrayList<Tile> rad = horizontalMovePro(rader.get(x),x,0);
            rader.set(x, tomListe);
            rader.set(x, rad);
        }
    }
    
    public ArrayList<Tile> horizontalMovePro(ArrayList<Tile> rad, int rowNumb, int z) {
        if (z == 1) Collections.reverse(rad);
        rad.removeIf(Tile -> Tile.getValue().equals(0));
        if (rad.size() > 1) {
            for (int y = rad.size()-1; y > 0; y--) {
                if (rad.get(y-1).canMerge(rad.get(y))){
                    rad = mergeWith(rad, y-1, y);
                    y -= 1;
                }
                else if (rad.get(y-1).getValue() != 0 && (rad.get(y).getValue()).equals(0)) {
                    Collections.swap(rad,y,y+1);
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
        return rad;
    }

    public ArrayList<Tile> addTiles(ArrayList<Tile> rad, int rowNumb) {
        if (rad.size() > 0 && rad.size() < 4) {
            for (int y=0; rad.size() < 4; y++) {
                rad.add(0, new Tile(rowNumb,y,0));
            }
        }
        return rad;

    }

    public void moveToLeft() {
        ArrayList<ArrayList<Tile>> rader = this.rows;
        ArrayList<Tile> tomListe = new ArrayList<>();
        for (int x=0; x < 4; x++) {
            ArrayList<Tile> rad = horizontalMovePro(rader.get(x),x,1);
            rader.set(x, tomListe);
            rader.set(x, rad);
        }
    }

    public boolean tileBiggerZero(Tile tile) {
        return tile.value > 0;
    }

    /* public ArrayList<ArrayList<Integer>> randomNewNumber() {
        ArrayList<String> emptySquare = new ArrayList<>();
        for (int x=0; x < 4; x++) {
            for (int y=0; y<4; y++) {
                if (rows.get(x).get(y).equals(0)){
                    emptySquare.add(x+"."+y);
                }
            }
        }
        if (emptySquare.size() == 0) {
            // Har vunnet!
            return this.rows;
        }

        Random ran = new Random();
        int i = ran.nextInt(11);
        int newSquare = ran.nextInt(emptySquare.size());
        String square = emptySquare.get(newSquare);
        int x = Integer.parseInt(square.substring(0,1));
        int y = Integer.parseInt(square.substring(2,3));
        
        if (i < 9) {
            i = 2;
            this.rows.get(x).set(y,i);
        }
        else {
            i = 4;
            this.rows.get(x).set(y,i);
        }
        return this.rows;
    } */

    @Override
    public String toString() {
        String utskrift = ""; 
        for (int x=0; x < 4; x++) {
            utskrift += "[";
            for (int y=0; y < 4; y++) {
                if (tileBiggerZero(this.rows.get(x).get(y))) {
                    utskrift += "["+this.rows.get(x).get(y).getValue()+"]";
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
        for (int y=0; y < lista.size(); y++) {
            if (tileBiggerZero(lista.get(y))) {
                utskrift += "["+lista.get(y).getValue()+"]";
            }
            else {
                utskrift += "[]";
            }
        }
        return utskrift;
    }

    public static void main(String[] args) {
        Grid grid = new Grid();
    
        
    }
}
