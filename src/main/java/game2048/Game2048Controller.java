package game2048;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.GridPane;

public class Game2048Controller {

    private Grid grid;

    @FXML 
    public GridPane gridView;

    @FXML
    public void initialize() {
        grid = new Grid();
        System.out.println(grid);
        createBoard();
        //drawBoard();
    }

    private void createBoard() {
        gridView.getChildren().clear();
        gridView.setPadding(new Insets(15,0,0,35));
        gridView.setHgap(10);
        gridView.setVgap(10);
        gridView.setStyle("-fx-background-color: "+"#808080");
        for (int r = 0; r < grid.rows.size(); r++) {
            for (int c = 0; c < grid.rows.get(r).size(); c++) {
                Label newTile = new Label(""+grid.rows.get(r).get(c).getValue());
                newTile.setPadding(new Insets(20,20,20,20));
                newTile.setStyle("-fx-background-color: " + grid.rows.get(r).get(c).getTileColor());
                gridView.add(newTile,c,r);
            }
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

    public void drawBoard() {
        for (int r = 0; r < grid.rows.size(); r++) {
            for (int c = 0; c < grid.rows.get(r).size(); c++) {
                int index = (r % grid.rows.size())*  grid.rows.size() + c;
                /* Node p = getTileFromGridPane(gridView, c, r);
                p.setStyle("-fx-background-color: " + grid.rows.get(r).get(c).getTileColor());
                ((Labeled) p).setText(""+""+grid.rows.get(c).get(r).getValue()); */
                gridView.getChildren().get(index)
                .setStyle("-fx-background-color: " + grid.rows.get(r).get(c).getTileColor());
                Node l = gridView.getChildren().get(index);
                ((Labeled) l).setText(""+""+grid.rows.get(r).get(c).getValue());
            }
        }
    }
}
