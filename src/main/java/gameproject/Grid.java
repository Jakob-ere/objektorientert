package gameproject;

import java.util.ArrayList;
import java.util.Random;

public class Grid {
    ArrayList<ArrayList<String>> rows = new ArrayList<>();

    public Grid() {
        makeGrid();
        System.out.println(this.rows);
        randomNewNumber();
        System.out.println(this.rows);
        randomNewNumber();
        randomNewNumber();
        System.out.println(this.rows);

    }

    public ArrayList<ArrayList<String>> makeGrid() {
        for (int x=0; x < 4; x++) {
            ArrayList<String> row = new ArrayList<String>();
            for (int y=0; y<4; y++) {
                row.add("0");
            }
            this.rows.add(row);
        }
        return this.rows;
    }

    public ArrayList<ArrayList<String>> randomNewNumber() {
        ArrayList<String> emptySquare = new ArrayList<>();
        for (int x=0; x < 4; x++) {
            for (int y=0; y<4; y++) {
                if (rows.get(x).get(y).equals("0")){
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
        int y = Integer.parseInt(square.substring(2, 3));
        
        if (i < 9) {
            i = 2;
            this.rows.get(x).set(y,i+"");
        }
        else {
            i = 4;
            this.rows.get(x).set(y,i+"");
        }
        return this.rows;
    }

    public static void main(String[] args) {
        Grid grid = new Grid();
    
        
    }
}
