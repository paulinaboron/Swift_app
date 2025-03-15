package org.example;

import java.sql.*;

public class test {
    public static void main(String[] args) {
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/swift_schema",
                    "root",
                    "pass"
            );

            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT * FROM codes");
            resultset.next();
            System.out.println(resultset.getString("NAME"));
            System.out.println(resultset.getString("ADDRESS"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
