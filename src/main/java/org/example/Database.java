package org.example;

import java.sql.*;

public class Database {
    private static Connection connection;

    public static void connect() {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com/sql7769026", "sql7769026", "33VR4J2Ksm");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}


