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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import loggin.LobbyController;

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
    private void eventAction(ActionEvent event) throws IOException {
        Object evt = event.getSource();
        if (evt.equals(btnLogin)) {
            if (!txtUser.getText().isEmpty() && !txtPassword.getText().isEmpty()) {//chyeca si esta vacio (si es diferente de vacio)
                String username = txtUser.getText();
                String password = txtPassword.getText();
                try {
                    // Conectar a la base de datos
                    Connection conn = DatabaseUtil.getConnection();

                    // Verificar si existe un usuario con el nombre de usuario y contraseña ingresados
                    PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM usuarios WHERE username=? AND password=?");
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    ResultSet rs = pstmt.executeQuery();

                    // Si existe un usuario, mostrar mensaje de éxito y cerrar la ventana de login
                    if (rs.next()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Login exitoso");
                        alert.setHeaderText(null);
                        alert.setContentText("Bienvenido, " + username + "!");
                        alert.showAndWait();
                        LobbyController lobby = new LobbyController();
                        lobby.muestraLobby(event);
                        txtUser.getScene().getWindow().hide();
                    } else {
                        // Si no existe un usuario, mostrar mensaje de error
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error de login");
                        alert.setHeaderText(null);
                        alert.setContentText("Nombre de usuario o contraseña incorrectos.");
                        alert.showAndWait();
                    }

                    // Cerrar la conexión y liberar los recursos
                    rs.close();
                    pstmt.close();
                    conn.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void eventRegister(ActionEvent event) throws IOException {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("ViewRegister.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
