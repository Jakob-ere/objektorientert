package game2048;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridTest {

    private Grid grid;
    private Grid actual;

    private void fillInNumber(Grid thisGrid, int row, int column, Integer value) {
        thisGrid.rows.get(row).set(column, new Tile(value));
    }

    private void fillUpRow(Grid thisGrid, int row, Integer value1, Integer value2, Integer value3, Integer value4) {
        fillInNumber(thisGrid, row, 0, value1);
        fillInNumber(thisGrid, row, 1, value2);
        fillInNumber(thisGrid, row, 2, value3);
        fillInNumber(thisGrid, row, 3, value4);
    }

    private void fillUpColumn(Grid thisGrid, int column, Integer value1, Integer value2, Integer value3, Integer value4) {
        fillInNumber(thisGrid, 0, column, value1);
        fillInNumber(thisGrid, 1, column, value2);
        fillInNumber(thisGrid, 2, column, value3);
        fillInNumber(thisGrid, 3, column, value4);
    }

    private void fillUpGrid(Grid thisGrid) {
        for (int r = 0; r < thisGrid.rows.size(); r++) {
            for (int c = 0; c < thisGrid.rows.get(r).size(); c++) {
                if (r % 2 == 0) {
                    if (c % 2 == 0) fillInNumber(thisGrid, r, c, 2);
                    else fillInNumber(thisGrid, r, c, 4);
                }
                else {
                    if (c % 2 == 0) fillInNumber(thisGrid, r, c, 4);
                    else fillInNumber(thisGrid, r, c, 2);
                }
            }
        }
    }
    
    @BeforeEach 
    public void setup() {
        grid = new Grid();
        actual = new Grid();
        fillInNumber(grid, 0, 0, 2);
        fillInNumber(grid, 1, 1, 2);
        fillInNumber(grid, 2, 2, 4);
        fillInNumber(grid, 3, 3, 4);
	}

    @Test 
    public void testMoveLeft() {
        fillUpColumn(actual, 0, 2, 2, 4, 4);
        grid.moveToLeft();
        assertEquals(grid.rows, actual.rows);
        fillUpRow(grid, 0, 2, 2, 0, 0);
        fillUpRow(grid, 1, 2, 4, 8, 8);
        fillUpRow(grid, 2, 4, 4, 4, 4);
        fillUpRow(grid, 3, 4, 0, 0, 4);
        grid.moveToLeft();
        fillUpColumn(actual, 0, 4, 2, 8, 8);
        fillUpColumn(actual, 1, 0, 4, 8, 0);
        fillUpColumn(actual, 2, 0, 16, 0, 0);
        assertEquals(grid.rows, actual.rows);
    }

    @Test 
    public void testMoveRight() {
        fillUpColumn(actual, 3, 2, 2, 4, 4);
        grid.moveToRight();
        assertEquals(grid.rows, actual.rows);
        fillUpRow(grid, 0, 2, 2, 0, 0);
        fillUpRow(grid, 1, 2, 4, 8, 8);
        fillUpRow(grid, 2, 4, 4, 4, 4);
        fillUpRow(grid, 3, 4, 0, 0, 4);
        grid.moveToRight();
        fillUpColumn(actual, 3, 4, 16, 8, 8);
        fillUpColumn(actual, 2, 0, 4, 8, 0);
        fillUpColumn(actual, 1, 0, 2, 0, 0);
        assertEquals(grid.rows, actual.rows);
    }

    @Test 
    public void testMoveUp() {
        fillUpRow(actual, 0, 2, 2, 4, 4);
        grid.moveUp();
        assertEquals(grid.rows, actual.rows);
        fillUpColumn(grid, 0, 2, 2, 0, 0);
        fillUpColumn(grid, 1, 2, 4, 8, 8);
        fillUpColumn(grid, 2, 4, 4, 4, 4);
        fillUpColumn(grid, 3, 4, 0, 0, 4);
        grid.moveUp();
        fillUpRow(actual, 0, 4, 2, 8, 8);
        fillUpRow(actual, 1, 0, 4, 8, 0);
        fillUpRow(actual, 2, 0, 16, 0, 0);
        assertEquals(grid.rows, actual.rows);
    }

    @Test 
    public void testMoveDown() {
        fillUpRow(actual, 3, 2, 2, 4, 4);
        grid.moveDown();
        assertEquals(grid.rows, actual.rows);
        fillUpColumn(grid, 0, 2, 2, 0, 0);
        fillUpColumn(grid, 1, 2, 4, 8, 8);
        fillUpColumn(grid, 2, 4, 4, 4, 4);
        fillUpColumn(grid, 3, 4, 0, 0, 4);
        grid.moveDown();
        fillUpRow(actual, 1, 0, 2, 0, 0);
        fillUpRow(actual, 2, 0, 4, 8, 0);
        fillUpRow(actual, 3, 4, 16, 8, 8);
        assertEquals(grid.rows, actual.rows);
    }

    @Test 
    public void testHasWon() {
        assertFalse(grid.hasWon());
        fillInNumber(grid, 3, 0, 2047);
        grid.updateBoard();
        assertEquals(grid.score, 2047);
        assertFalse(grid.hasWon());
        fillInNumber(grid, 3, 0, 2048);
        grid.updateBoard();
        assertTrue(grid.hasWon());
    }

    @Test 
    public void testHasLost() {
        assertFalse(grid.hasLost());
        fillUpGrid(grid);
        grid.updateBoard();
        assertEquals(grid.score, 4);
        assertTrue(grid.hasLost());
        fillInNumber(grid, 0, 1, 2);
        assertFalse(grid.hasLost());
        fillInNumber(grid, 0, 1, 4);
        fillInNumber(grid, 0, 2, 0);
        assertFalse(grid.hasLost());
    }

    @Test
    public void testRandomNumber() {
        assertEquals(grid.emptyList().size(), 12);
        grid.randomNewNumber();
        assertEquals(grid.emptyList().size(), 11);
        fillUpGrid(grid);
        assertThrows(IllegalStateException.class, () -> {
            grid.randomNewNumber();
        }, "IllegalState skal utløses når grid er full.");
    }

    @Test 
    public void testCheckIfMoveHappens() {
        ArrayList<ArrayList<Tile>> copy = grid.copyBoard();
        ArrayList<ArrayList<Tile>> rader = null;
        assertEquals(copy, grid.rows);
        assertFalse(grid.checkIfMoveHappens(copy));
        grid.moveToLeft();
        assertTrue(grid.checkIfMoveHappens(copy));
        assertThrows(NullPointerException.class, () -> {
            grid.checkIfMoveHappens(rader);
        }, "IllegalState skal utløses når grid er full.");
    }
}
