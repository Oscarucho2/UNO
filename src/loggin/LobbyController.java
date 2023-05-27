package loggin;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LobbyController {

    private Button getoutbtn;
    //Constructor
    public LobbyController() {
    } 
    
    @FXML
    public void muestraLobby(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ViewLobby.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void getout(ActionEvent event)throws IOException
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    
}
