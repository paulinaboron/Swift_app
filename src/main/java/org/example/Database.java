package org.example;

import org.jdbi.v3.core.Jdbi;

import java.sql.*;

public class Database {
    private static Connection connection;

    public static void connect() {
        try{
//            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/swift_schema", "root", "pass");
            connection = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com/sql7769026", "sql7769026", "33VR4J2Ksm");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
