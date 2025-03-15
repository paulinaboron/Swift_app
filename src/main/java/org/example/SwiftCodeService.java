package org.example;

import org.example.Model.Branch;
import org.example.Model.Headquarter;
import org.example.Model.SwiftCode;
import org.jdbi.v3.core.Handle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SwiftCodeService {
    public static Headquarter getHeadquarter(String swiftCode) {
        ResultSet resultset;
        Headquarter hq = new Headquarter();
        String query = "SELECT * FROM codes WHERE `SWIFT CODE` = ?";

        try (PreparedStatement pst = Database.getConnection().prepareStatement(query)) {
            pst.setString(1, swiftCode);
            resultset = pst.executeQuery();
            resultset.next();
            System.out.println(resultset.getString("NAME"));
            System.out.println(resultset.getString("ADDRESS"));

            hq.setSwiftCode(resultset.getString("SWIFT CODE"));
            hq.setBankName(resultset.getString("NAME"));
            hq.setAddress(resultset.getString("ADDRESS"));
            hq.setCountryISO2(resultset.getString("COUNTRY ISO2 CODE"));
            hq.setCountryName(resultset.getString("COUNTRY NAME"));
            hq.setHeadquarter(true);
            hq.setBranches(getBranchesList(swiftCode));

            System.out.println(hq.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hq;
    }

    public static List<Branch> getBranchesList(String swiftCode){
        ResultSet resultset;
        List<Branch> list = new ArrayList<>();
        String query = "SELECT * FROM codes WHERE `SWIFT CODE` LIKE CONCAT(LEFT(?, 8), '%') AND `SWIFT CODE` != ?;";
        try (PreparedStatement pst = Database.getConnection().prepareStatement(query)) {
            pst.setString(1, swiftCode);
            pst.setString(2, swiftCode);
            resultset = pst.executeQuery();
            while(resultset.next()){
                Branch branch = new Branch();
                branch.setSwiftCode(resultset.getString("SWIFT CODE"));
                branch.setBankName(resultset.getString("NAME"));
                branch.setAddress(resultset.getString("ADDRESS"));
                branch.setCountryISO2(resultset.getString("COUNTRY ISO2 CODE"));
                branch.setCountryName(resultset.getString("COUNTRY NAME"));
                branch.setHeadquarter(false);
                list.add(branch);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Branch getBranch(String swiftCode){
        ResultSet resultset;
        Branch branch = new Branch();
        String query = "SELECT * FROM codes WHERE `SWIFT CODE` = ?";

        try (PreparedStatement pst = Database.getConnection().prepareStatement(query)) {
            pst.setString(1, swiftCode);
            resultset = pst.executeQuery();
            resultset.next();
            System.out.println(resultset.getString("NAME"));
            System.out.println(resultset.getString("ADDRESS"));

            branch.setSwiftCode(resultset.getString("SWIFT CODE"));
            branch.setBankName(resultset.getString("NAME"));
            branch.setAddress(resultset.getString("ADDRESS"));
            branch.setCountryISO2(resultset.getString("COUNTRY ISO2 CODE"));
            branch.setCountryName(resultset.getString("COUNTRY NAME"));
            branch.setHeadquarter(false);

            System.out.println(branch);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branch;
    }

    public static List<SwiftCode> getByCountry(String countryISO2) {
        try (Handle handle = Database.getJdbi().open()) {
            return handle.createQuery("SELECT * FROM codes WHERE `COUNTRY ISO2 CODE` = :countryISO2")
                    .bind("countryISO2", countryISO2)
                    .mapToBean(SwiftCode.class)
                    .list();
        }
    }

    public static void addSwiftCode(SwiftCode swiftCode) {
        try (Handle handle = Database.getJdbi().open()) {
            handle.createUpdate("INSERT INTO codes (`COUNTRY ISO2 CODE`, `SWIFT CODE`, `CODE TYPE`, `NAME`, `ADDRESS`, is_headquarter) VALUES (:swiftCode, :bankName, :address, :countryISO2, :countryName, :isHeadquarter)")
                    .bindBean(swiftCode)
                    .execute();
        }
    }

    public static void deleteSwiftCode(String swiftCode) {
        try (Handle handle = Database.getJdbi().open()) {
            handle.createUpdate("DELETE FROM codes WHERE `SWIFT CODE` = :swiftCode")
                    .bind("swiftCode", swiftCode)
                    .execute();
        }
    }



}
