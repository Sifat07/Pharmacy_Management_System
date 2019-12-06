/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author nishan
 */
public class ProductManager {
    
    private Connection connection;
    
    public ProductManager() {
        connection = DBConnection.connect();
    }
    
    public ObservableList<ProductAdpaterList> getData(String searchquery) {
        
        ObservableList<ProductAdpaterList> productlist = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT id, name, ppu, available FROM product";
        if(searchquery != null && searchquery.length() > 0) {
            query += searchquery;
            System.out.println("\n"+query);
        }
        
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            int serial = 0;
            while (resultSet.next()) {
                serial++;
                productlist.add(
                    new ProductAdpaterList(
                        serial,
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("ppu"),
                        resultSet.getInt("available")
                    )
                );
//                System.out.println(resultSet.getInt("id")+" "+
//                        resultSet.getString("name")+" "+
//                        resultSet.getDouble("ppu")+" "+
//                        resultSet.getInt("available")+" ");
            }
            
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productlist;
    }
    
    public void insert(ProductAdpaterList product) {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO product(name, category, ppu, available) VALUES (?, NULL, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPpu());
            preparedStatement.setInt(3, product.getAvailable());
            //preparedStatement.setInt(3, product.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(ProductAdpaterList product) {
        PreparedStatement preparedStatement = null;
        String query = "UPDATE product SET name = ?, ppu = ?, available = ?  WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPpu());
            preparedStatement.setInt(3, product.getAvailable());
            preparedStatement.setInt(4, product.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM product WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
