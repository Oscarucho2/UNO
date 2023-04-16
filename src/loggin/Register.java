package loggin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import java.sql.PreparedStatement;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.ResultSet;

public class Register {

    private Connection conn;
    @FXML
    private TextField txtUserRegister;
    private TextField txtEmailRegister;
    @FXML
    private PasswordField txtPasswordRegister;
    @FXML
    private Button btnRegister;

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();
        if (evt.equals(txtUserRegister)) {

            if (event.getCharacter().equals(" ")) {//si es igual al espacio detenlo
                event.consume();
            }

        } else if (evt.equals(txtPasswordRegister)) {
            if (event.getCharacter().equals(" ")) {//si es igual al espacio detenlo
                event.consume();
            }
        } else if (evt.equals(txtEmailRegister)) {
            if (event.getCharacter().equals(" ")) {//si es igual al espacio detenlo
                event.consume();
            }
        }
    }

    @FXML
    private void eventAction(ActionEvent event) {
        // Establecer la información de conexión
        String url = "jdbc:mysql://localhost:3306/unodatabase?serverTimezone=UTC";
        String user = "root";
        String password = "a987612345";

        //Obtenemos los datos del usuario a registrar
        String username = txtUserRegister.getText();
        String passwords = txtPasswordRegister.getText();
        String email = txtEmailRegister.getText();

        // Crear la conexión
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos");
            String insertQuery = "INSERT INTO usuarios (username, password, email) VALUES (?, ?, ?)";
            String selectQuery = "SELECT LAST_INSERT_ID()";
            // Preparar la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, username);
            pstmt.setString(2, passwords);
            pstmt.setString(3, email);
            // Ejecutar la consulta SQL
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                // Obtener el último id_usuario insertado
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int idUsuario = rs.getInt(1);
                    System.out.println("El usuario ha sido registrado exitosamente con id " + idUsuario);
                }
                rs.close();
            }
            // Cerrar la conexión
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
        }

    }
}
