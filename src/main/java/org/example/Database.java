package org.example;

import org.jdbi.v3.core.Jdbi;

import java.sql.*;

public class Database {
    private static Connection connection;
    private static Jdbi jdbi;

    public static void connect2() {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/swift_schema", "root", "pass");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void connect() {
        jdbi = Jdbi.create("jdbc:mysql://127.0.0.1:3306/swift_schema", "root", "pass");
    }
    public static Jdbi getJdbi() {
        return jdbi;
    }

    public static Connection getConnection() {
        return connection;
    }
}
