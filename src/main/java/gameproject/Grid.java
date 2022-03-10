package gameproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Grid {
    ArrayList<ArrayList<Integer>> rows = new ArrayList<>();

    public Grid() {
        makeGrid();
        System.out.println(this.rows);
        randomNewNumber();
        System.out.println(this.rows);
        moveRight();
        System.out.println(this.rows);

    }

    public void makeGrid() {
        for (int x=0; x < 4; x++) {
            ArrayList<Integer> row = new ArrayList<Integer>();
            for (int y=0; y<4; y++) {
                row.add(0);
            }
            this.rows.add(row);
        }
    }

    public void moveRight() {
        ArrayList<ArrayList<Integer>> rader = this.rows;
        ArrayList<Integer> tomListe = new ArrayList<>(Arrays.asList(0,0,0,0));
        for (int x=0; x < 4; x++) {
            ArrayList<Integer> rad = horizontalMove(rader.get(x));
            rader.set(x,tomListe);
            rader.set(x, rad);
        }
    }

    public ArrayList<Integer> horizontalMove(ArrayList<Integer> rad) {
        for (int x=0; x < 4; x++) {
            if (x < 3) {
                if (rad.get(x) != 0 && rad.get(x+1).equals(0)) {
                    Collections.swap(rad,x,x+1);
                }
            }
        }
        return rad;
        
    }
    
    /* public ArrayList<Tile> horizontalMove(ArrayList<Tile> rad, int z) {
        if (z == 1) Collections.reverse(rad);
        System.out.println(printeMande(rad));
        for (int x=0; x < 4; x++) {
            if (x < 3) {
                if (rad.get(x).canMerge(rad.get(x+1))) {
                    rad.get(x).mergeWith(rad.get(x+1));
                    rad.get(x+1).value = 0;
                    Collections.swap(rad,x, x+1);
                }
                if (rad.get(x).getValue() != 0 && (rad.get(x+1).getValue()).equals(0)) {
                    Collections.swap(rad,x,x+1);
                }
                if (x < 2 && rad.get(x).getValue() != 0){
                    if (rad.get(x+1).getValue()!= 0 && rad.get(x+2).getValue().equals(0)) {
                        Collections.swap(rad, x+1, x+2);
                        Collections.swap(rad, x, x+1);
                    }
                }
            }
        }
        if (z==1) Collections.reverse(rad);
        return rad;
    } */

     /* public ArrayList<Tile> horizontalMovePro(ArrayList<Tile> rad, int z) {
        ArrayList<Tile> copi = new ArrayList<>(rad);
        radComparator comparator = new radComparator();
        Collections.sort(copi, comparator);
        System.out.println(printeMande(copi));
        System.out.println("---------");
        if (z == 1) Collections.reverse(rad);
        rad.removeIf(Tile -> Tile.getValue().equals(0));
        if (rad.size() > 1) {
            mergeRow(rad);
        }
        rad = addTiles(rad);
        if (z == 1) Collections.reverse(rad);
        return rad;
    } */

    public ArrayList<ArrayList<Integer>> randomNewNumber() {
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
    }

    public static void main(String[] args) {
        Grid grid = new Grid();
    
        
    }
}
