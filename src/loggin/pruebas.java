/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loggin;

import com.sun.javafx.logging.Logger;
import com.sun.javafx.logging.PlatformLogger.Level;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javax.print.DocFlavor.URL;
import javax.swing.JOptionPane;

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

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt=event.getSource();
        if(evt.equals(txtUser)){
            
            if(event.getCharacter().equals(" ")){//si es igual al espacio detenlo
                event.consume();
            }
              
        }else if(evt.equals(txtPassword)){
            if(event.getCharacter().equals(" ")){//si es igual al espacio detenlo
                event.consume();
            }
        }
    }

    @FXML
    private void eventAction(ActionEvent event) {
        Object evt=event.getSource();
        if(evt.equals(btnLogin)){
          if(!txtUser.getText().isEmpty() && !txtPassword.getText().isEmpty()){//chyeca si esta vacio (si es diferente de vacio)
                String user=txtUser.getText();
                String pass=txtPassword.getText();
                System.out.println("Usuario"+user + "\n"+"Contraseña"+pass);
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
    /*public void initialize(URL url, ResourceBundle rb){
        
    }*/
/*
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private void loadStage(String url, Event event){

        try {

            //((Node)(event.getSource())).getScene().getWindow().hide();    


            Object eventSource = event.getSource(); 
            Node sourceAsNode = (Node) eventSource ;
            Scene oldScene = sourceAsNode.getScene();
            Window window = oldScene.getWindow();
            Stage stage = (Stage) window ;
            stage.hide();

            Parent root = FXMLLoader.load(getClass().getResource(url));
            Scene scene = new Scene(root);              
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();  

            newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                }
            });  

        } catch (IOException ex) {
            //Logger.getLogger(pruebas.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }    
*/
}
