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
public class Loggin extends Application{

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        try{
                
        Parent root= FXMLLoader.load(getClass().getResource("ViewLoggin.fxml"));
        Scene scene= new Scene(root);
        primaryStage.setScene(scene);//loader scebe 
        primaryStage.show();//show scene
        
        
    }catch(Exception e){
           e.printStackTrace();
       } 
        
    }
    
    public static void main(String [] args ){
        launch(args);
    }
}
