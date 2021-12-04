package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() {
        if(connection==null) {
            try {
                final String url =  "jdbc:mysql://localhost:3306/test";
                final String user = "root";
                final String password = "1234";
                connection = DriverManager.getConnection(url,user,password);
                if(connection!=null) {
                    System.out.println("Connection Success!");
                }
            } catch (SQLException e) {
                System.out.println("Connection Failed: " + e.getMessage());
            }
        }
        return connection;
    }

}
