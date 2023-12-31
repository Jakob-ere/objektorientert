package game2048;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game2048App extends Application{

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("2048");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("game2048.fxml"))));
        primaryStage.show();
    }

}

