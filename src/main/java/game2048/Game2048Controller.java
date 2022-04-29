package game2048;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Game2048Controller {

    private Grid grid;
    private IGameHandler saveHandler = new GameHandler();
    private boolean gameStillActive;

    @FXML
    private TextField saveInput;

    @FXML 
    private GridPane gridView;

    @FXML
    private Pane paneView;

    @FXML 
    private Text scoreView;

    @FXML
    private Pane paneGameDone;

    @FXML
    private void initialize() {
        grid = new Grid();
        grid.randomNewNumber();
        grid.randomNewNumber();
        System.out.println(grid);
        createBoard();
        gameStillActive = true;
    }

    private void createBoard() {
        grid.updateBoard();
        scoreView.setText(String.valueOf(grid.score));
        gridView.getChildren().clear();
        gridView.setPadding(new Insets(15,0,0,35));
        gridView.setHgap(10);
        gridView.setVgap(10);
        gridView.setStyle("-fx-background-color: "+"#808080");
        for (int r = 0; r < grid.rows.size(); r++) {
            for (int c = 0; c < grid.rows.get(r).size(); c++) {
                Label newTile = new Label("");
                if (grid.rows.get(r).get(c).greaterThanZero()) {
                    newTile.setText(String.valueOf(grid.rows.get(r).get(c).getValue()));
                }
                newTile.setAlignment(Pos.CENTER);
                newTile.setMinHeight(50);
                newTile.setMinWidth(50);
                newTile.setStyle("-fx-background-color: " + grid.rows.get(r).get(c).getTileColor());
                gridView.add(newTile,c,r);
            }
        }
    }

    @FXML
    private void keyPressMove(KeyEvent event) {
        KeyCode keyPress = event.getCode();
        if (keyPress.equals(KeyCode.UP)) moveBoardUp();
        else if (keyPress.equals(KeyCode.LEFT)) moveBoardLeft();
        else if (keyPress.equals(KeyCode.RIGHT)) moveBoardRight();
        else if (keyPress.equals(KeyCode.DOWN)) moveBoardDown();
    }
    
    @FXML
    private void moveBoardUp() {
        if (gameStillActive) {
            ArrayList<ArrayList<Tile>> copy = grid.copyBoard();
            grid.moveUp();
            if (grid.checkIfMoveHappens(copy)) grid.randomNewNumber();
            else throw new IllegalStateException("Not a valid move");
            drawBoard();
            checkGameDone();
        }
    }

    @FXML
    private void moveBoardDown() {
        if (gameStillActive) {
            ArrayList<ArrayList<Tile>> copy = grid.copyBoard();
            grid.moveDown();
            if (grid.checkIfMoveHappens(copy)) grid.randomNewNumber();
            else throw new IllegalStateException("Not a valid move");
            drawBoard();
            checkGameDone();
        }
    }

    @FXML
    private void moveBoardLeft() {
        if (gameStillActive) {
            ArrayList<ArrayList<Tile>> copy = grid.copyBoard();
            grid.moveToLeft();
            if (grid.checkIfMoveHappens(copy)) grid.randomNewNumber(); 
            else throw new IllegalStateException("Not a valid move");
            drawBoard();
            checkGameDone();
        }
    }

    @FXML
    private void moveBoardRight() {
        if (gameStillActive) {
            ArrayList<ArrayList<Tile>> copy = grid.copyBoard();
            grid.moveToRight();
            if (grid.checkIfMoveHappens(copy)) grid.randomNewNumber(); 
            else throw new IllegalStateException("Not a valid move");
            drawBoard();
            checkGameDone();
        }
    }

    @FXML
    private void saveFile() {
        try {
            saveHandler.writeGrid(getFile(), grid);
        } catch (IOException e) {
            showMessage("Your game couldn't be written to a file!");
        }
    }

    @FXML 
    private void loadFile() {
        try {
            saveHandler.readGrid(getFile(), grid);
        } catch (FileNotFoundException e) {
            showMessage("The game was not found!");
        }
        drawBoard();
        checkGameDone();
    }

    private String getFile(){
        String filename = this.saveInput.getText();
        if (filename.isEmpty()) return "game_file";
        return filename;
    }

    private void drawWon() {
        Text doneText = new Text();
        doneText.setText("You Won!");
        doneText.setFill(Color.GREEN);
        doneText.setStyle("-fx-font-size: 45px;");
        doneText.setTranslateX(40);
        doneText.setTranslateY(40);
        paneGameDone.getChildren().add(doneText);
    }

    private void drawLoss() {
        Text doneText = new Text();
        doneText.setText("You Lost!");
        doneText.setStyle("-fx-font-size: 45px;");
        doneText.setFill(Color.RED);
        doneText.setTranslateX(40);
        doneText.setTranslateY(40);
        paneGameDone.getChildren().add(doneText);
    }

    private void checkGameDone(){
        if (grid.hasWon()) {
            drawWon();
            gameStillActive = false;
        } else if (grid.hasLost()) {
            drawLoss();
            gameStillActive = false;
        }
        else {
            paneGameDone.getChildren().clear();
            gameStillActive = true;
        }
    }

    private void drawBoard() {
        for (int r = 0; r < grid.rows.size(); r++) {
            for (int c = 0; c < grid.rows.get(r).size(); c++) {
                int index = (r % grid.rows.size()) * grid.rows.size() + c;
                gridView.getChildren().get(index)
                .setStyle("-fx-background-color: " + grid.rows.get(r).get(c).getTileColor());
                Node l = gridView.getChildren().get(index);
                ((Labeled) l).setText("");
                if (grid.rows.get(r).get(c).greaterThanZero()) {
                    ((Labeled) l).setText(String.valueOf(grid.rows.get(r).get(c).getValue()));
                }
            }
        }
        scoreView.setText(String.valueOf(grid.score));
    }

    private void showMessage(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong.");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}
