 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author sifat
 */
public class RegisterController extends RenderView implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repassword;
    @FXML
    private Button backtologn;
    @FXML
    private Button signup;
    @FXML
    private Label msg;
    @FXML
    private Button selectphoto;
    @FXML
    private ImageView imgview;
    @FXML
    private Button clear;
    
    private User user;
    private FileInputStream imgsrc;
    private Image image;
    private String imagepath = "default.png";
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        user = new User();
        System.err.println("user: "+user.uid);
        System.out.println("db.RegisterController.initialize()");
        if(user.uid == 0) {
            
            try {
                imgsrc = new FileInputStream(imagepath);
                image = new Image(imgsrc);
                imgview.setImage(image);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }    

    @FXML
    private void register(ActionEvent event) {
//        System.out.print("in Regusf");
        
        if(name.getText().length() > 0 && email.getText().length() > 0 && username.getText().length() > 0 &&  password.getText().length() > 0 &&  repassword.getText().length() > 0){
            String msgcontent = "";
            
            String mail = email.getText();
            List<String> checkmail = new ArrayList<>();
            if(mail.contains("@") && mail.contains(".")) {
                try{
                    checkmail.addAll(Arrays.asList(mail.split("\\.")));
                    checkmail.addAll(Arrays.asList(checkmail.get(0).split("@")));
                    checkmail.remove(0);
                    if(checkmail.size() < 3){
                        msgcontent += "Please enter valid email address\n";
                    }
                }catch (Exception e){
                    msgcontent += "Please enter valid email address\n";
                }
            }else{
                msgcontent += "Please enter valid email address\n";
            }
            
            if(!password.getText().equals(repassword.getText())) {
                msgcontent += "Type your password correctly\n";
            }
            
            if(msgcontent.length() > 0){
                msg.setText(msgcontent);
            }else{
                boolean status = user.registerUser(name.getText(), email.getText(), username.getText(),  password.getText(), imagepath);
                if(status) {
                    gotoPage(event, "/assets/Login.fxml", "");
                }else{
                    msg.setText("Failed to create user");
                }
            }
        }else {
            msg.setText("Please fill up the form");
        }
    }
    
    @FXML
    private  void gotoLogin(ActionEvent event) {
        gotoPage(event, "/assets/Login.fxml", "");
    }

    @FXML
    private void selectImage(ActionEvent event) {
        FileChooser filechooser = new FileChooser();
        filechooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );
        filechooser.setTitle("Select Image");
        
        File selectedfile = filechooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        String newfilename = "userimg./" + username.getText() + selectedfile.getName().split("\\.")[selectedfile.getName().split("\\.").length - 1];
        File destinationfile = new File(newfilename);
        if (selectedfile != null){
            try{
                Files.copy(selectedfile.toPath(), destinationfile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                System.out.println(destinationfile.getAbsolutePath());
                imgsrc = new FileInputStream(newfilename);
                image = new Image(imgsrc);
                imgview.setImage(image);
                imagepath = newfilename;
                clear.setOpacity(1);
            }catch (IOException e ) {
                e.printStackTrace();
            }
        }
       
    }

    @FXML
    private void clearImage(ActionEvent event) {
        try {
            imagepath = "default.png";
            imgsrc = new FileInputStream(imagepath);
            image = new Image(imgsrc);
            imgview.setImage(image);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        clear.setOpacity(0);
    }
    
}
