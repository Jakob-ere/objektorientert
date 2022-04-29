package game2048;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Grid {
    public ArrayList<ArrayList<Tile>> rows = new ArrayList<>();
    public int score = 0;

    public Grid() {
        createGrid();
    }

    public void createGrid() {
        for (int r=0; r < 4; r++) {
            ArrayList<Tile> row = new ArrayList<Tile>();
            for (int c=0; c < 4; c++) {
                row.add(new Tile(r,c,0));
            }
            this.rows.add(row);
        }
    }

    public void moveUp() {
        rotateGrid("right");
        moveHorizontal("right");
        rotateGrid("left");
        updateBoard();
    } 

    public void moveDown() {
        rotateGrid("left");
        moveHorizontal("right");
        rotateGrid("rigth");
        updateBoard();
    }

    public void moveToRight() {
        moveHorizontal("right");
        updateBoard();
    }

    public void moveToLeft() {
        moveHorizontal("left");
        updateBoard();
    }

    private void rotateGrid(String direction) {
        ArrayList<ArrayList<Tile>> newRows = new ArrayList<>();
        for (int r = 0; r < 4; r++) {
            ArrayList<Tile> rad = addTiles(new ArrayList<Tile>());
            newRows.add(rad);
        }
        int rowSize = rows.size();
        for (int r = 0; r < rowSize; r++) {
            int columnSize = rows.get(r).size();
            for (int c = 0; c < columnSize; c++) {
                if (direction.equals("left")) newRows.get(rowSize-1-c).set(r, rows.get(r).get(c));  
                else newRows.get(c).set(rowSize-1-r, rows.get(r).get(c));
            }
        }
        this.rows = newRows;
    }

    private void moveHorizontal(String direction) {
        int z = 0;
        if (direction.equals("left")) z = 1;
        ArrayList<ArrayList<Tile>> rader = this.rows;
        ArrayList<Tile> tomListe = new ArrayList<>();
        for (int y=0; y < 4; y++) {
            ArrayList<Tile> rad = rowSort(rader.get(y),z);
            rader.set(y, tomListe);
            rader.set(y, rad);
        }
        this.rows = rader;
    }

    private ArrayList<Tile> rowSort(ArrayList<Tile> rad, int z) {
        RowComparator comparator = new RowComparator();
        ArrayList<Tile> copy = new ArrayList<>(rad);
        if (z == 1) Collections.reverse(copy);
        Collections.sort(copy, comparator);
        mergeRow(copy);
        addTiles(copy);
        if (z == 1) Collections.reverse(copy);
        return copy;
    }

    private ArrayList<Tile> mergeRow(ArrayList<Tile> rad) {
        for (int x = rad.size()-1; x > 0; x--) {
            if (rad.get(x).canMerge(rad.get(x-1))){
                rad = mergeTiles(rad, x-1, x);
                x -= 1;
            }
        }
        return rad;
    }
    private ArrayList<Tile> mergeTiles(ArrayList<Tile> rad, int x1, int x2) {
        rad.get(x2).mergeWithTile(rad.get(x1));
        rad.remove(x1);
        return rad;
    }

    private ArrayList<Tile> addTiles(ArrayList<Tile> rad) {
        if (rad.size() > 4) throw new IllegalStateException("A row can not have more than 4 tiles");
        while (rad.size() < 4) {
            rad.add(0, new Tile(0));
        }
        return rad;
    }

    public void updateBoard() {
        int highestNumber = 0;
        for (int r = 0; r < rows.size(); r++) {
            for (int c = 0; c < rows.get(r).size(); c++) {
                rows.get(r).get(c).setColumn(c);
                rows.get(r).get(c).setRow(r);
                if (rows.get(r).get(c).getValue() > highestNumber) {
                    highestNumber = rows.get(r).get(c).getValue();
                }
            }
        }
        this.score = highestNumber;
    }

    public ArrayList<ArrayList<Tile>> randomNewNumber() {
        ArrayList<Tile> emptySquares = emptyList();
        if (emptySquares.size() < 1) throw new IllegalStateException("The board is full, it can't place a new one.");
        Random ran = new Random();
        int value = ran.nextInt(11);
        int newTile = ran.nextInt(emptySquares.size());
        int rowCord = emptySquares.get(newTile).getRow();
        int columnCord = emptySquares.get(newTile).getColumn();
        rows.get(rowCord).set(columnCord, new Tile(rowCord,columnCord,value >= 7 ? 4 : 2));
        return rows;
    }

    public ArrayList<Tile> emptyList() {
        ArrayList<Tile> emptySquares = new ArrayList<>();
        for (int r=0; r < 4; r++) {
            for (int c=0; c < 4; c++) {
                if (!rows.get(r).get(c).greaterThanZero()) {
                    emptySquares.add(rows.get(r).get(c));
                } 
            }
        }
        return emptySquares;
    }

    public boolean hasWon() {
        return this.score >= 2048;
    }

    public ArrayList<ArrayList<Tile>> copyBoard() {
        if (rows != null) {
            ArrayList<ArrayList<Tile>> copi = new ArrayList<>();
            for(int r = 0; r < rows.size(); r++) {
                copi.add(rows.get(r));
            }
            return copi;
        } else throw new IllegalStateException("Cannot copy a non existent grid.");
    }

    public boolean checkIfMoveHappens(ArrayList<ArrayList<Tile>> copy) {
        if (copy != null || this.rows != null) {
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) { 
                    if (!this.rows.get(r).get(c).getValue().equals(copy.get(r).get(c).getValue())) {
                       return true;
                    }
                }
            }
        } else throw new IllegalStateException("One of the two grids can't be null.");
        return false;
    }

    public boolean hasLost() {
        int tilesBiggerThanZero = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (rows.get(r).get(c).greaterThanZero()) tilesBiggerThanZero += 1;
                if (r + 1 < 4 && rows.get(r).get(c).canMerge(rows.get(r+1).get(c))) return false;
                if (c + 1 < 4 && rows.get(r).get(c).canMerge(rows.get(r).get(c+1))) return false;
            }
        }
        if (tilesBiggerThanZero <= 15) return false;
        return true;
    }

    @Override
    public String toString() {
        String utskrift = ""; 
        for (int y=0; y < rows.size(); y++) {
            utskrift += "[";
            for (int x=0; x < 4; x++) {
                if (rows.get(y).get(x).greaterThanZero()) {
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
            if (lista.get(x).greaterThanZero()) {
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
        grid.rows.get(0).set(0, new Tile(16));
        grid.rows.get(0).set(1, new Tile(16));
        grid.rows.get(0).set(2, new Tile(32));
        grid.rows.get(0).set(3, new Tile(16));
        System.out.println(grid.rows);
        grid.moveToLeft();
        System.out.println(grid.rows);
    }
}
