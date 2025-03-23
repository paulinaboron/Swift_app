package org.example.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Branch {
    private String address;
    private String bankName;
    private String countryISO2;
    @JsonProperty("isHeadquarter")
    private boolean isHeadquarter;
    private String swiftCode;



    public void parseData(ResultSet rs){
        try {
            this.swiftCode = rs.getString("SWIFT CODE");
            this.bankName = rs.getString("NAME");
            this.address = rs.getString("ADDRESS");
            this.countryISO2 = rs.getString("COUNTRY ISO2 CODE");
            this.isHeadquarter = this.swiftCode.endsWith("XXX");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
