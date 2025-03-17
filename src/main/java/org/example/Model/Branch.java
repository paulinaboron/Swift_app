package org.example.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Branch {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    @JsonProperty("isHeadquarter")
    private boolean isHeadquarter;
    private String swiftCode;

    public String toString_short() {
        return "{swiftCode: " + swiftCode  +
                ", bankName: " + bankName  +
                ", address: " + address  +
                ", countryISO2: " + countryISO2  +
                ", isHeadquarter: " + isHeadquarter +
                '}';
    }

    @Override
    public String toString() {
        return "{swiftCode: " + swiftCode  +
                ", bankName: " + bankName  +
                ", address: " + address  +
                ", countryISO2: " + countryISO2  +
                ", country name: " + countryName +
                ", isHeadquarter: " + isHeadquarter +
                '}';
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setIsHeadquarter(boolean isHeadquarter) {
        this.isHeadquarter = isHeadquarter;
    }

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
            this.isHeadquarter = this.swiftCode.endsWith("XXX");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
