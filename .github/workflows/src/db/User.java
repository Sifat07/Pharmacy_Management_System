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

/**
 *
 * @author nishan
 */
class User {
    public static int uid;
    public static String name;
    public static String email;
    public static String username;
    public static String password;
    public static String image;
    
    private Connection connection = null;
    
    public User() {
        connection = DBConnection.connect();
    }
    
    public boolean login(String uname, String pass) {
        Boolean status = false;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        String query = "SELECT * FROM user WHERE username = ? AND pass = ?";
        
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, pass);
            result = preparedStatement.executeQuery();
            if (result.next()) {
                //System.out.println(result.getString("name")+" "+result.getString("email"));
                uid = result.getInt("id");
                name = result.getString("name");
                email = result.getString("email");
                username = result.getString("username");
                password = result.getString("pass");
                image = result.getString("image");
                
                status = true;
            }
            preparedStatement.close();
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public static void logout() {
        uid = 0;
        name = null;
        email = null;
        username = null;
        password = null;
        image = null;
        
    }
    
    public boolean registerUser(String name, String email, String uname, String pass, String imagepath) {
        System.out.println(name+"\n"+email+"\n"+uname+"\n"+pass);
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO user(name, email, username, pass, image) VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, uname);
            preparedStatement.setString(4, pass);
            preparedStatement.setString(5, imagepath);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
