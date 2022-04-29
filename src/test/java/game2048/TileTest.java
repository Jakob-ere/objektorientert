package game2048;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TileTest {
    
    private Tile tile;
    private Tile otherTile;

    @BeforeEach 
    public void setup() {
        tile = new Tile(128);
        otherTile = new Tile(2, 3, 32);
	}

    @Test
    public void testCanMerge() {
        assertFalse(tile.canMerge(otherTile));
        assertThrows(IllegalArgumentException.class, () -> {
            Tile newTile = new Tile(-10);
        }, "IllegalArgument skal utløses om en verdi er negativ");
        Tile newTile = new Tile(128);
        assertFalse(otherTile.canMerge(newTile));
        assertTrue(tile.canMerge(newTile));
    }

    @Test
    public void testMergeWithTile() {
        tile.mergeWithTile(otherTile);
        assertEquals(tile.getValue(), 160);
        assertThrows(IllegalArgumentException.class, () -> {
            Tile newTile = new Tile(1,6,32);
        }, "IllegalArgument skal utløses om en koordinat ikke er mellom 0 og 3");
        Tile newTile = new Tile(32);
        tile.mergeWithTile(newTile);
        assertEquals(tile.getValue(), 192);
    }

    @Test 
    public void testGetTileColor() {
        assertEquals(tile.getTileColor(),"#f9dd53");
        assertEquals(otherTile.getTileColor(),"#f67c5f");
        assertThrows(IllegalArgumentException.class, () -> {
            Tile newTile = new Tile(1,-2,32);
        }, "IllegalArgument skal utløses om en koordinat ikke er mellom 0 og 3");
        Tile newTile = new Tile(0);
        Tile secondNewTile = new Tile(212);
        assertEquals(newTile.getTileColor(), "#c8dcd2");
        assertEquals(secondNewTile.getTileColor(), "");
    }   
}
