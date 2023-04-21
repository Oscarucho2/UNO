/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package loggin;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author miguel
 */
public class Loggin extends Application {

    private Stage primaryStage;
    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            this.primaryStage = primaryStage;
            this.loader = new FXMLLoader(getClass().getResource("ViewLoggin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
      public void showNewWindow() throws IOException {
        FXMLLoader newLoader = new FXMLLoader(getClass().getResource("ViewLoggin.fxml"));
        Parent newRoot = newLoader.load();
        Scene newScene = new Scene(newRoot);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }
          public void showMainWindow() {
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
