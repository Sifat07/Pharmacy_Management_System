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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author nishan
 */
public class ProductPopupController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField unitprice;
    @FXML
    private TextField quantity;
    @FXML
    private Button save;

    private int id = 0;
    
    ProductManager pm = new ProductManager();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setField(ProductAdpaterList product) {
        this.id = product.getId();
        this.name.setText(product.getName());
        this.unitprice.setText(Double.toString(product.getPpu()));
        this.quantity.setText(Integer.toString(product.getAvailable()));
    }
    
    @FXML
    public void save(ActionEvent event) {
        //System.out.println(id);
        if(id == 0) {
            ProductAdpaterList product = new ProductAdpaterList(0, 0, name.getText(), Double.parseDouble(unitprice.getText()), Integer.parseInt(quantity.getText()));
            pm.insert(product);
        }else if(id > 0) {
            ProductAdpaterList product = new ProductAdpaterList(0, id, name.getText(), Double.parseDouble(unitprice.getText()), Integer.parseInt(quantity.getText()));
            pm.update(product);
        }
        //((Node)event.getSource()).getScene().getWindow()
        save.getScene().getWindow().hide();
    }
}
