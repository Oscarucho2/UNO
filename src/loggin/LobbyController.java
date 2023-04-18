package loggin;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LobbyController {

    //Constructor
    public LobbyController() {
    } 
    
    @FXML
    public void muestraLobby(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ViewPrincipal.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
