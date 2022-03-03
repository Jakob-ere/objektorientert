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
        moveRight();
        System.out.println(this);
        moveLeft();
        System.out.println(this);
       

    }

    public void makeGrid() {
        for (int x=0; x < 4; x++) {
            ArrayList<Tile> row = new ArrayList<Tile>();
            for (int y=0; y<4; y++) {
                if (y == 2) row.add(new Tile(x,y,1));
                else {
                    row.add(new Tile(x,y,0));
                }
            }
            this.rows.add(row);
        }
    }

    public void moveRight() {
        ArrayList<ArrayList<Tile>> rader = this.rows;
        ArrayList<Tile> tomListe = new ArrayList<>();
        for (int x=0; x < 4; x++) {
            ArrayList<Tile> rad = horizontalMove(rader.get(x),0);
            rader.set(x, tomListe);
            rader.set(x, rad);
        }
    }

    public ArrayList<Tile> horizontalMove(ArrayList<Tile> rad, int z) {
        if (z == 1) Collections.reverse(rad);
        for (int x=0; x < 4; x++) {
            if (x < 3) {
                if (rad.get(x).getValue() != 0 && (rad.get(x+1).getValue()).equals(0)) {
                    Collections.swap(rad,x,x+1);
                }
            }
        }
        if (z==1) Collections.reverse(rad);
        return rad;
    }

    public void moveLeft() {
        ArrayList<ArrayList<Tile>> rader = this.rows;
        ArrayList<Tile> tomListe = new ArrayList<>();
        for (int x=0; x < 4; x++) {
            ArrayList<Tile> rad = horizontalMove(rader.get(x),1);
            rader.set(x, tomListe);
            rader.set(x, rad);
        }
        
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
            for (int y=0; y < 4; y++) {
                utskrift += "[";
                utskrift += "["+this.rows.get(x).get(y).getValue()+"]";
            }
            utskrift += "]\n";
        }
        return utskrift;
    }

    public static void main(String[] args) {
        Grid grid = new Grid();
    
        
    }
}
