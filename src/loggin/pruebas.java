/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loggin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author miguel
 */
public class pruebas {
    // private UserDAO model =new UserDAO();

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    private Button btnregister;
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();
        if (evt.equals(txtUser)) {

            if (event.getCharacter().equals(" ")) {//si es igual al espacio detenlo
                event.consume();
            }

        } else if (evt.equals(txtPassword)) {
            if (event.getCharacter().equals(" ")) {//si es igual al espacio detenlo
                event.consume();
            }
        }
    }

    @FXML
    private void eventAction(ActionEvent event) {
        Object evt = event.getSource();
        if (evt.equals(btnLogin)) {
            if (!txtUser.getText().isEmpty() && !txtPassword.getText().isEmpty()) {//chyeca si esta vacio (si es diferente de vacio)
                String user = txtUser.getText();
                String pass = txtPassword.getText();
                System.out.println("Usuario " + user + "\n" + "Contraseña " + pass);
                //int state=model.login(user, pass);
                //if(state!=-1)
                //  if(state==1){
                //    JOptionPane.showMessageDialog(null, "Datos correctamente ingresados");
                //loadStage("ViewPrincipal",event);//manda a llamar principal
                //}else
                //  JOptionPane.showMessageDialog(null,"Error al iniciar sesion, datos incorrectos",
                //        "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                //}else
                //  JOptionPane.showMessageDialog(null,"Error al iniciar sesion, datos incorrecttos", "ADVERTENCIA",
                //        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @FXML
    private void eventRegister(ActionEvent event) throws IOException {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("ViewRegister.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
