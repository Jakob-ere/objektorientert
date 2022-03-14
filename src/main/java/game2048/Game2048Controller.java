package game2048;

import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Game2048Controller {

    private Grid grid;

    @FXML 
    public GridPane gridView;

    @FXML 
    public VBox vBoox;


    @FXML 
    public Pane paneView;

    @FXML
    public void initialize() {
        grid = new Grid();
        System.out.println(grid);
        createBoard();
        drawBoard();
        createPaneBoard();
        drawPaneBoard();
    }

    private void createBoard() {
        gridView.getChildren().clear();
        gridView.setPadding(new Insets(15,0,0,35));
        gridView.setHgap(10);
        gridView.setVgap(10);
        gridView.setStyle("-fx-background-color: "+"#808080");
        for (int r = 0; r < grid.rows.size(); r++) {
            for (int c = 0; c < grid.rows.get(r).size(); c++) {
                Label newTile = new Label(""+grid.rows.get(c).get(r).getValue());
                newTile.setPadding(new Insets(20,20,20,20));
                gridView.add(newTile,c,r);
            }
        }
    }

    private void createPaneBoard() {
        paneView.getChildren().clear();
        paneView.setStyle("-fx-background-color: #808080");
        vBoox.setStyle("-fx-background-color: "+"#808080");
        for (int r = 0; r < grid.rows.size(); r++) {
            HBox hBox = new HBox();
            for (int c = 0; c < grid.rows.get(r).size(); c++) {
                Label newTile = new Label(""+grid.rows.get(c).get(r).getValue());
                newTile.setPadding(new Insets(20,20,20,20));
                hBox.getChildren().add(newTile);
            }
            vBoox.getChildren().add(hBox);
        }
    }
    
    @FXML
    public void moveBoardUp() {
        grid.moveUp();
        drawBoard();
    }

    @FXML
    public void moveBoardDown() {
        grid.moveDown();
        drawBoard();
    }

    @FXML
    public void moveBoardLeft() {
        grid.moveToLeft();
        drawBoard();
    }

    @FXML
    public void moveBoardRight() {
        grid.moveToRight();
        drawBoard();
    }

    private Node getTileFromGridPane(GridPane gridView, int col, int row) {
        for (Node node : gridView.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public void drawPaneBoard() {
        for (int r = 0; r < grid.rows.size(); r++) {
            for (int c = 0; c < grid.rows.get(r).size(); c++) {
                vBoox.getChildren().get(c).setStyle("-fx-background-color: " + grid.rows.get(r).get(c).getTileColor());
               /*  gridView.getChildren().get(r*4 + c)
                .setStyle("-fx-background-color: " + grid.rows.get(r).get(c).getTileColor());
                Node l = gridView.getChildren().get(r*4 + c);
                ((Labeled) l).setText(""+""+grid.rows.get(c).get(r).getValue()); */
            }
        }
    }


    public void drawBoard() {
        for (int r = 0; r < grid.rows.size(); r++) {
            for (int c = 0; c < grid.rows.get(r).size(); c++) {
                Node p = getTileFromGridPane(gridView, c, r);
                p.setStyle("-fx-background-color: " + grid.rows.get(r).get(c).getTileColor());
                ((Labeled) p).setText(""+""+grid.rows.get(c).get(r).getValue());
               /*  gridView.getChildren().get(r*4 + c)
                .setStyle("-fx-background-color: " + grid.rows.get(r).get(c).getTileColor());
                Node l = gridView.getChildren().get(r*4 + c);
                ((Labeled) l).setText(""+""+grid.rows.get(c).get(r).getValue()); */
            }
        }
    }
}
