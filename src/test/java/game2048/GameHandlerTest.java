package game2048;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameHandlerTest {
    
    private Grid grid;
    private GameHandler gameHandler;

    private void fillInNumber(Grid thisGrid, int row, int column, Integer value) {
        thisGrid.rows.get(row).set(column, new Tile(value));
    }

    private static final String testForLagring = """
            4
            2;0;0;0
            0;2;0;0
            0;0;4;0
            0;0;0;4
            """.replaceAll("\\R", System.getProperty("line.separator"));

    private static final String feilLagretFil = """
            4
            2;0;0;0
            0;2;0;0
            0;0;4;0
            """.replaceAll("\\R", System.getProperty("line.separator"));

    @BeforeAll
    public void setup() throws IOException{
        grid = new Grid();
        fillInNumber(grid, 0, 0, 2);
        fillInNumber(grid, 1, 1, 2);
        fillInNumber(grid, 2, 2, 4);
        fillInNumber(grid, 3, 3, 4);
        grid.updateBoard();
        gameHandler = new GameHandler();
        Files.write(GameHandler.getFile("testForLagring").toPath(), testForLagring.getBytes());
        Files.write(GameHandler.getFile("feilLagretFil").toPath(), feilLagretFil.getBytes());
    }

    @Test
    public void testLoadGame() {
        Grid newGame = new Grid();
        try {
            newGame = gameHandler.readGrid("testForlagring", newGame);
        } catch (FileNotFoundException e) {
            fail("Kunne ikke laste opp fil");
        }
        assertEquals(newGame.rows, grid.rows);
        assertEquals(newGame.score, grid.score);
    }

    @Test 
    public void testLoadInvalidGame() {
        assertThrows(Exception.class,() -> {
            gameHandler.readGrid("feilLagretFil", grid);
        },"Det skal utløses en feil om filen ikke har hele griden eller score.");
    }

    @Test 
    public void testLoadNonExistentGame() {
        assertThrows(IOException.class,() -> {
            gameHandler.readGrid("ikkeEnFil", grid);
        },"IOException skal utløses om en fil ikke finnes.");
    }

    @Test
    public void testSavingGame() throws IOException {
        try {
            gameHandler.writeGrid("nyFil", grid);
        } catch (FileNotFoundException e){
            fail("Kunne ikke lagre spillet");
        }
        byte[] testNyFil = null;
        byte[] testForlagring = null;
        try {
            testNyFil = Files.readAllBytes(GameHandler.getFile("nyFil").toPath());
        } catch (Exception e) {
            fail("Kunne ikke laste opp den ny-lagrede filen");
        }
        try {
            testForlagring = Files.readAllBytes(GameHandler.getFile("testForLagring").toPath());
        } catch (Exception e) {
            fail("Kunne ikke laste opp testForLagring-filen");
        }
        assertNotNull(testNyFil);
        assertNotNull(testForlagring);
        try {
            for (int i = 0; i < testNyFil.length; i++) {
                assertEquals(testNyFil[i], testForlagring[i]);
            }
        } catch (Exception e) {
            fail("Filene er ikke like");
        } 
    }

    @AfterAll 
    public static void tearDown() {
        GameHandler.getFile("nyFil").delete();
        GameHandler.getFile("feilLagretFil").delete();
        GameHandler.getFile("testForLagring").delete();
    }
}
