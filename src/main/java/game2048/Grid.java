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

    //creates a grid with 4x4 tiles with the value 0
    public void createGrid() {
        for (int r=0; r < 4; r++) {
            ArrayList<Tile> row = new ArrayList<Tile>();
            for (int c=0; c < 4; c++) {
                row.add(new Tile(r,c,0));
            }
            this.rows.add(row);
        }
    }

    //move the tiles in the grid upwards
    public void moveUp() {
        rotateGrid("right");
        moveHorizontal("right");
        rotateGrid("left");
        updateBoard();
    } 

    //move the tiles in the grid downwards
    public void moveDown() {
        rotateGrid("left");
        moveHorizontal("right");
        rotateGrid("rigth");
        updateBoard();
    }

    //move the tiles in the grid to the right
    public void moveToRight() {
        moveHorizontal("right");
        updateBoard();
    }

    //move the tiles in the grid to the left
    public void moveToLeft() {
        moveHorizontal("left");
        updateBoard();
    }

    //rotates the grid 90 degrees left or right, depending on the argument
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

    //sends the rows to the rowSort()-method and sets it equals to this.rowss
    private void moveHorizontal(String direction) {
        int z = 0;
        if (direction.equals("left")) z = 1;
        ArrayList<ArrayList<Tile>> copyRows = this.rows;
        ArrayList<Tile> emptyListe = new ArrayList<>();
        for (int y=0; y < 4; y++) {
            ArrayList<Tile> row = rowSort(copyRows.get(y),z);
            copyRows.set(y, emptyListe);
            copyRows.set(y, row);
        }
        this.rows = copyRows;
    }
    
    //the row get sorted and returns the sorted row.
    private ArrayList<Tile> rowSort(ArrayList<Tile> row, int z) {
        RowComparator comparator = new RowComparator();
        ArrayList<Tile> copy = new ArrayList<>(row);
        if (z == 1) Collections.reverse(copy);
        Collections.sort(copy, comparator);
        mergeRow(copy);
        addTiles(copy);
        if (z == 1) Collections.reverse(copy);
        return copy;
    }

    //checks if some tiles can be merge in the row and then sends it to mergeTiles,
    //returns the mergedRow
    private ArrayList<Tile> mergeRow(ArrayList<Tile> row) {
        for (int x = row.size()-1; x > 0; x--) {
            if (row.get(x).canMerge(row.get(x-1))){
                row = mergeTiles(row, x-1, x);
                x -= 1;
            }
        }
        return row;
    }

    //merges the two tiles and returns the row
    private ArrayList<Tile> mergeTiles(ArrayList<Tile> row, int x1, int x2) {
        row.get(x2).mergeWithTile(row.get(x1));
        row.remove(x1);
        return row;
    }

    //adds tiles until the list has 4 tiles.
    private ArrayList<Tile> addTiles(ArrayList<Tile> row) {
        if (row.size() > 4) throw new IllegalStateException("A row can not have more than 4 tiles");
        while (row.size() < 4) {
            row.add(0, new Tile(0));
        }
        return row;
    }

    //updates the score and the coordinates of the tiles
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

    //adds a randomnumber, either 2 or 4, to the board
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

    //creates a list of the tiles that have value = 0
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

    //copies the rows of a grid-object.
    public ArrayList<ArrayList<Tile>> copyBoard() {
        if (rows.size() < 3 || rows.size() > 0) {
            ArrayList<ArrayList<Tile>> copi = new ArrayList<>();
            for(int r = 0; r < rows.size(); r++) {
                copi.add(rows.get(r));
            }
            return copi;
        } else throw new IllegalStateException("Cannot copy a non existent grid.");
    }

    //checks if some move or merge has happened
    public boolean checkIfMoveHappens(ArrayList<ArrayList<Tile>> copy) {
        if (copy != null || this.rows != null) {
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) { 
                    if (!this.rows.get(r).get(c).getValue().equals(copy.get(r).get(c).getValue())) {
                       return true;
                    }
                }
            }
        } else throw new NullPointerException("One of the two grids can't be null.");
        return false;
    }

    //checks if score is greater than 2048
    public boolean hasWon() {
        return this.score >= 2048;
    }

    //checks if board can move in any directions
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
}
