package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Model.Branch;
import org.example.Model.Country;
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
            hq.parseData(resultset);
            hq.setBranches(getBranchesList(swiftCode));
        } catch (SQLException e) {
            return null;
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
                branch.parseData(resultset);
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
            branch.parseData(resultset);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branch;
    }

    public static Country getByCountry(String countryISO2) {
        ResultSet resultSet;
        Country country = new Country();
        List<Branch> list = new ArrayList<>();
        String query = "SELECT * FROM codes WHERE `COUNTRY ISO2 CODE` = ?";
        try (PreparedStatement pst = Database.getConnection().prepareStatement(query)) {
            pst.setString(1, countryISO2);
            resultSet = pst.executeQuery();

            resultSet.next();

            country.setCountryName(resultSet.getString("COUNTRY NAME"));
            country.setCountryISO2(resultSet.getString("COUNTRY ISO2 CODE"));
            Branch branch1 = new Branch();
            branch1.parseData(resultSet);
            list.add(branch1);

            while(resultSet.next()){
                Branch branch = new Branch();
                branch.parseData(resultSet);
                list.add(branch);
            }
            country.setBranches(list);

        } catch (Exception e) {
//            throw new RuntimeException(e);
            return null;
        }
        return country;
    }

    public static void addSwiftCode(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Branch branch = objectMapper.readValue(body, Branch.class);
        System.out.println(branch);
        String query = "INSERT INTO codes (`COUNTRY ISO2 CODE`, `SWIFT CODE`, `NAME`, `ADDRESS`, `COUNTRY NAME`) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = Database.getConnection().prepareStatement(query)) {
            pst.setString(1, branch.getCountryISO2());
            pst.setString(2, branch.getSwiftCode());
            pst.setString(3, branch.getBankName());
            pst.setString(4, branch.getAddress());
            pst.setString(5, branch.getCountryName());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteSwiftCode(String swiftCode) {
        String query = "DELETE FROM codes WHERE `SWIFT CODE` = ?";
        try (PreparedStatement pst = Database.getConnection().prepareStatement(query)) {
            pst.setString(1, swiftCode);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
