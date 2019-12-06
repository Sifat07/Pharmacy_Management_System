/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author nishan
 */
public class LoginController extends RenderView implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private Button login;
    @FXML
    private Label msg;
    @FXML
    private PasswordField password;

    //RenderView renderview = new RenderView();
    
    private User user = new User();
    @FXML
    private Button signup;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("db.LoginController.initialize()");
    }    

    @FXML
    private void login(ActionEvent event) {
        String pass = password.getText();
        String uname = username.getText();
        
        if(user.login(uname, pass)){
            
            //msg.setText("Logged In");
            
            gotoPage(event, "/assets/Dashboard.fxml", "");
        } else {
            msg.setText("Incorrect user name or password");
        }
        
        System.out.print(uname+" "+pass);
    }
    
    @FXML
    private void gotoSignup(ActionEvent event) {
        gotoPage(event, "/assets/Register.fxml", "/assets/register.css");
    }
}
