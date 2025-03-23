package org.example.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Bank {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    @JsonProperty("isHeadquarter")
    private boolean isHeadquarter;
    private String swiftCode;


    public String getSwiftCode() {
        return swiftCode;
    }

    public String getCountryISO2(){
        return countryISO2;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getAddress() {
        return address;
    }

    public String getBankName() {
        return bankName;
    }

    public void parseData(ResultSet rs){
        try {
            this.swiftCode = rs.getString("SWIFT CODE");
            this.bankName = rs.getString("NAME");
            this.address = rs.getString("ADDRESS");
            this.countryISO2 = rs.getString("COUNTRY ISO2 CODE");
            this.countryName = rs.getString("COUNTRY NAME");
            this.isHeadquarter = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
