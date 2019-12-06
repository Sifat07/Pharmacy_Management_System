/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sifat
 */
class DBConnection {
    
    public static Connection connect() {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.sqlite.JDBC");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Connected");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
