package loggin;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Register {

    Stage primaryStage;
    private Connection conn;
    @FXML
    private TextField txtUserRegister;
    @FXML
    private TextField txtEmailRegister;
    @FXML
    private PasswordField txtPasswordRegister;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnback;

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
    private void eventAction(ActionEvent event) throws IOException {
        // Establecer la información de conexión
        String url = "jdbc:mysql://localhost:3306/unodatabase?serverTimezone=UTC";
        String user = "root";
        String password = "a987612345";

        // Obtener los datos del usuario
        String username = txtUserRegister.getText();
        String passwords = txtPasswordRegister.getText();
        String email = txtEmailRegister.getText();

        try {
            // Obtener la dirección IP de la máquina local
            InetAddress ip = InetAddress.getLocalHost();
            // Convertir la dirección IP a una cadena
            String ipAddress = ip.getHostAddress();

            // Crear la conexión
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos");
            String insertQuery = "INSERT INTO usuarios (username, password, email,ip_address) VALUES (?, ?, ?, ?)";

            // Preparar la consulta SQL
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, username);
            pstmt.setString(2, passwords);
            pstmt.setString(3, email);
            pstmt.setString(4, ipAddress);
            // Ejecutar la consulta SQL
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("El usuario ha sido registrado exitosamente.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro exitoso!");
                alert.setHeaderText(null);
                alert.setContentText("Bienvenido, " + username + "!");
                alert.showAndWait();
                Loggin log = new Loggin();
                log.showNewWindow();
            }

            // Cerrar la conexión
            conn.close();

        } catch (SQLException ex) {
            System.out.println("Error al registrar el usuario: " + ex.getMessage());
        }
    }

    @FXML
    public void backLoggin(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Loggin log = new Loggin();
        log.showNewWindow();
    }
}
