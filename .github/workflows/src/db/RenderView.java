/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author nishan
 */
public class RenderView {
    
    
    public void gotoPage(ActionEvent event, String layout, String style) {
        try {
            ((Node)event.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(layout));
            Scene scene = new Scene(root);
            if(style != null || style != "") {
                scene.getStylesheets().add(getClass().getResource(style).toExternalForm());
            }
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void productentryPopup(ActionEvent event, String layout, String style, ProductAdpaterList product) {
        try {
            //.getScene().getWindow().hide()
//            ((Node)event.getSource()).getScene().getStylesheets().add("/assets/gray.css");
            
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource(layout).openStream());
            Scene scene = new Scene(root);
            
            
            if(style != null || style != "") {
                scene.getStylesheets().add(getClass().getResource(style).toExternalForm());
            }
            
            if(product != null) {
                ProductPopupController ppc = (ProductPopupController) loader.getController();
                ppc.setField(product);
            }
            stage.addEventHandler(WindowEvent.WINDOW_HIDING, new  EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
//                    TView tv = new TView();
//                    tv.arrange(table);
                    //System.out.println("write your code here");
                }
            });
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
