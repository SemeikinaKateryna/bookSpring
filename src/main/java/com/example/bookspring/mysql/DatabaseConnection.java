package com.example.bookspring.mysql;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final static String URL = "jdbc:mysql://localhost:3306/books";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "Katya";

    public DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException |
                 NoSuchMethodException | InvocationTargetException ex) {
            System.out.println("SQLState: " + ex);
            System.out.println("VendorError: " + ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
