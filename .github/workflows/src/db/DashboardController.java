/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author sifat
 */
public class DashboardController extends RenderView implements Initializable {

    @FXML
    private Button logout;
    @FXML
    private Button addproduct;
    @FXML
    private Button editproduct;
    @FXML
    private Button deleteproduct;
    @FXML
    private TextField searchfield;
    @FXML
    private Button searchproduct;
    @FXML
    private MenuButton searchby;
    
    @FXML
    private TableView<ProductAdpaterList> tableview;
    @FXML
    private TableColumn<ProductAdpaterList, Integer> id;
    @FXML
    private TableColumn<ProductAdpaterList, Integer> sl;
    @FXML
    private TableColumn<ProductAdpaterList, String> name;
    @FXML
    private TableColumn<ProductAdpaterList, Double> unitprice;
    @FXML
    private TableColumn<ProductAdpaterList, Integer> available;
    @FXML
    private TableColumn<ProductAdpaterList, Double> total;
    @FXML
    private Button clearsearch;
    @FXML
    private Label selectedattr;
    @FXML
    private ImageView userimage;
    @FXML
    private Label usernamelabel;
    
    ProductManager pm;
    private final String[] dbcolumn = {"name", "ppu", "available"};
    private final String[] items = {"Name", "Unit Price", "Quantity"};
    private String searchbykey = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
//    @SuppressWarnings("empty-statement")
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try {
            System.out.println(User.uid+" "+User.name+" "+User.image);
            userimage.setImage(new Image(new FileInputStream(User.image)));
            usernamelabel.setText(User.name);
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        pm = new ProductManager();
        System.out.println(pm.getData(null));
        
        for(String item: items){
            MenuItem menuitem = new MenuItem(item);
            menuitem.setOnAction((event) -> {
                //System.out.println("From event: "+menuitem.getText());
                searchfield.setPromptText(item);
                selectedattr.setText(menuitem.getText());
                searchbykey = menuitem.getText();
            });
            searchby.getItems().add(menuitem);
        }
        
        id.setCellValueFactory(new PropertyValueFactory<ProductAdpaterList, Integer>("id"));
        sl.setCellValueFactory(new PropertyValueFactory<ProductAdpaterList, Integer>("serial"));
        name.setCellValueFactory(new PropertyValueFactory<ProductAdpaterList, String>("name"));
        unitprice.setCellValueFactory(new PropertyValueFactory<ProductAdpaterList, Double>("ppu"));
        available.setCellValueFactory(new PropertyValueFactory<ProductAdpaterList, Integer>("available"));
        total.setCellValueFactory(new PropertyValueFactory<ProductAdpaterList, Double>("total"));
        
        /* Set event on click row*/
        
        tableview.setRowFactory(tableview -> {
            TableRow<ProductAdpaterList> row = new TableRow<ProductAdpaterList>();
            row.setOnMouseClicked(event -> {
                ProductAdpaterList rowData = row.getItem();
                try{
                    if (null != rowData && rowData.getId() > 0) {
                        
                        System.out.println(rowData.getName()+" "+rowData.getId());
                        editproduct.setDisable(false);
                        deleteproduct.setDisable(false);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                
            });
            return row; //To change body of generated lambdas, choose Tools | Templates.
        });
        
        tableview.setItems(pm.getData(null));
        
        
    }    

    @FXML
    private void logout(ActionEvent event) {
        User.logout();
        gotoPage(event, "/assets/Login.fxml", "");
    }

    @FXML
    private void addProduct(ActionEvent event) {
        productentryPopup(event, "/assets/ProductPopup.fxml", "/assets/productpopup.css", null);
        manageEditDeleteButton();
        reoladTable();
    }

    @FXML
    private void editProduct(ActionEvent event) {
        ProductAdpaterList pal = tableview.getSelectionModel().getSelectedItem();
        productentryPopup(event, "/assets/ProductPopup.fxml", "/assets/productpopup.css", pal);
        
        manageEditDeleteButton();
        reoladTable();
    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        ProductAdpaterList pal = tableview.getSelectionModel().getSelectedItem();
        System.out.println(pal.getId() + " " + pal.getName());
        pm.delete(pal.getId());
        manageEditDeleteButton();
        reoladTable();
    }

    @FXML
    private void searchProduct(ActionEvent event) {
        String condition = "";
        if(!"".equals(searchfield.getText())){
            if(searchbykey == null){
                condition = " WHERE name LIKE '"+searchfield.getText()+"'";
            }else{
                for(int i = 0; i < items.length; i++) {
                    
                    if(items[i].equals(searchbykey)){
                        
                        if(dbcolumn[i].equals("name")){
                            condition = " WHERE name LIKE '"+searchfield.getText()+"'";
                        }else{
                            condition = " WHERE "+dbcolumn[i]+" = '"+searchfield.getText()+"'";
                        }
                        break;
                    }
                }
            }
        }
        tableview.setItems(pm.getData(condition));
    }
    
    @FXML
    private void clearSearch(ActionEvent event) {
        selectedattr.setText("Name");
        searchfield.setText("");
        searchfield.setPromptText("Name");
        searchbykey = null;
        tableview.setItems(pm.getData(null));
        System.out.println(User.name);
    }
    
    private void manageEditDeleteButton() {
        TableRow<ProductAdpaterList> row = new TableRow<ProductAdpaterList>();
        ProductAdpaterList rowData = row.getItem();
        try{
            if (null != rowData && rowData.getId() > 0) {

                editproduct.setDisable(false);
                deleteproduct.setDisable(false);
            } else {
                editproduct.setDisable(true);
                deleteproduct.setDisable(true);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void reoladTable() {
        //pm = new ProductManager();
        System.out.println("Table reloaded");
        tableview.setItems(pm.getData(null));
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
