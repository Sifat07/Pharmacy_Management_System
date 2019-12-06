/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author sifat
 */
public class Db extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/assets/Login.fxml"));
        
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/assets/login.css").toExternalForm());
        stage.getIcons().add(new Image("file:C:\\Users\\sifat\\Documents\\NetBeansProjects\\Pharmacy\\src\\assets\\Images\\Logo.png"));
        //Image icon = new Image(getClass().getResourceAsStream("/assets/images/Logo.png"));
        stage.setTitle("Pharmacy Repository");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
    
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
