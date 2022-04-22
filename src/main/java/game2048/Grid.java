package game2048;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Grid {
    ArrayList<ArrayList<Tile>> rows = new ArrayList<>();
    public int score = 0;

    public Grid() {
        makeGrid();
        randomNewNumber();
        randomNewNumber();
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
    } 

    public void moveDown() {
        rotateGrid("left");
        moveHorizontal("right");
        rotateGrid("rigth");
        updateCordinates();
    }

    public void moveToRight() {
        moveHorizontal("right");
        updateCordinates();
    }

    public void moveToLeft() {
        moveHorizontal("left");
        updateCordinates();
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
            ArrayList<Tile> rad = moveHorizontalSort(rader.get(y),z);
            rader.set(y, tomListe);
            rader.set(y, rad);
        }
        this.rows = rader;
    }

    private ArrayList<Tile> moveHorizontalSort(ArrayList<Tile> rad, int z) {
        RowComparator comparator = new RowComparator();
        ArrayList<Tile> copi = new ArrayList<>(rad);
        if (z == 1) Collections.reverse(copi);
        Collections.sort(copi, comparator);
        mergeRow(copi,z);
        addTiles(copi);
        if (z == 1) Collections.reverse(copi);
        return copi;
    }

    private ArrayList<Tile> mergeRow(ArrayList<Tile> rad,int z) {
        for (int x = rad.size()-1; x > 0; x--) {
            if (rad.get(x).canMerge(rad.get(x-1))){
                rad = tileMergeWithTile(rad, x-1, x);
                x -= 1;
            }
        }
        return rad;
    }
    private ArrayList<Tile> tileMergeWithTile(ArrayList<Tile> rad, int x1, int x2) {
        rad.get(x1).mergeWithTile(rad.get(x2));
        Collections.swap(rad, x1, x2);
        rad.remove(rad.get(x1));
        return rad;
    }

    private ArrayList<Tile> addTiles(ArrayList<Tile> rad) {
        if (rad.size() < 4) {
            for (int x=0; rad.size() < 4; x++) {
                rad.add(0, new Tile(0));
            }
        }
        return rad;
    }

    public void updateCordinates() {
        int highestNumber = 0;
        for (int r = 0; r < rows.size(); r++) {
            for (int c = 0; c < rows.get(r).size(); c++) {
                rows.get(r).get(c).updateColumn(c);
                rows.get(r).get(c).updateRow(r);
                if (rows.get(r).get(c).getValue() > highestNumber) {
                    highestNumber = rows.get(r).get(c).getValue();
                }
            }
        }
        this.score = highestNumber;
    }

    public boolean tileBiggerZero(Tile tile) {
        return tile.value > 0;
    }

    public ArrayList<ArrayList<Tile>> randomNewNumber() {
        ArrayList<Tile> emptySquares = emptyList();
        Random ran = new Random();
        int value = ran.nextInt(11);
        int newTile = ran.nextInt(emptySquares.size());
        int rowCord = emptySquares.get(newTile).getRow();
        int columnCord = emptySquares.get(newTile).getColumn();
        rows.get(rowCord).set(columnCord, new Tile(rowCord,columnCord,value >= 7 ? 4 : 2));
        return rows;
    }

    private ArrayList<Tile> emptyList() {
        ArrayList<Tile> emptySquares = new ArrayList<>();
        for (int r=0; r < 4; r++) {
            for (int c=0; c < 4; c++) {
                if (!tileBiggerZero(rows.get(r).get(c))) {
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
        ArrayList<ArrayList<Tile>> copi = new ArrayList<>();
        for(int r = 0; r < rows.size(); r++) {
            copi.add(rows.get(r));
        }
        return copi;
    }

    public boolean checkIfMoveHappens(ArrayList<ArrayList<Tile>> copy) {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) { 
                if (!this.rows.get(r).get(c).getValue().equals(copy.get(r).get(c).getValue())) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean hasLost() {
        int tilesBiggerThanZero = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (tileBiggerZero(rows.get(r).get(c))) tilesBiggerThanZero += 1;
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
        System.out.println(grid.score);
    }
}
