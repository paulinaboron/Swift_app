package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.example.Model.Bank;
import org.example.Model.Branch;
import org.example.Model.Country;
import org.example.Model.Headquarter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.apache.log4j.builders.appender.SocketAppenderBuilder.LOGGER;

public class SwiftCodeService {
    private final Connection connection;

    public SwiftCodeService(Connection connection) {
        this.connection = connection;
    }

    public Headquarter getHeadquarter(String swiftCode) {
        ResultSet resultset;
        Headquarter hq = new Headquarter();
        String query = "SELECT * FROM codes WHERE `SWIFT CODE` = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, swiftCode);
            resultset = pst.executeQuery();
            if(!resultset.next()) return null;
            hq.parseData(resultset);
            hq.setBranches(getBranchesList(swiftCode));
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Database error", e);
            return null;
        }
        return hq;
    }

    public List<Branch> getBranchesList(String swiftCode){
        ResultSet resultset;
        List<Branch> list = new ArrayList<>();
        String query = "SELECT * FROM codes WHERE `SWIFT CODE` LIKE CONCAT(LEFT(?, 8), '%') AND `SWIFT CODE` != ?;";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, swiftCode);
            pst.setString(2, swiftCode);
            resultset = pst.executeQuery();
            while(resultset.next()){
                Branch branch = new Branch();
                branch.parseData(resultset);
                list.add(branch);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Database error", e);
        }
        return list;
    }

    public Bank getBank(String swiftCode){
        ResultSet resultset;
        Bank bank = new Bank();
        String query = "SELECT * FROM codes WHERE `SWIFT CODE` = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, swiftCode);
            resultset = pst.executeQuery();
            if(!resultset.next()) return null;
            bank.parseData(resultset);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Database error", e);
            return null;
        }
        return bank;
    }

    public Country getByCountry(String countryISO2) {
        ResultSet resultSet;
        Country country = new Country();
        List<Branch> list = new ArrayList<>();
        String query = "SELECT * FROM codes WHERE `COUNTRY ISO2 CODE` = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, countryISO2);
            resultSet = pst.executeQuery();

            if(!resultSet.next()) return null;

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
            LOGGER.log(Level.ERROR, "Database error", e);
            return null;
        }
        return country;
    }

    public int addSwiftCode(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Bank bank = objectMapper.readValue(body, Bank.class);
            System.out.println(bank);
            String query = "INSERT INTO codes (`COUNTRY ISO2 CODE`, `SWIFT CODE`, `NAME`, `ADDRESS`, `COUNTRY NAME`) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, bank.getCountryISO2());
                pst.setString(2, bank.getSwiftCode());
                pst.setString(3, bank.getBankName());
                pst.setString(4, bank.getAddress());
                pst.setString(5, bank.getCountryName());
                return pst.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Database error", e);
            return 0;
        }
    }

    public int deleteSwiftCode(String swiftCode) {
        String query = "DELETE FROM codes WHERE `SWIFT CODE` = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, swiftCode);
            return pst.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Database error", e);
            return 0;
        }
    }
}
