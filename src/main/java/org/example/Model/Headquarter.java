package org.example.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Headquarter {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private String swiftCode;
    private List<Branch> branches;


    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }


    public void parseData(ResultSet rs){
        try {
            this.swiftCode = rs.getString("SWIFT CODE");
            this.bankName = rs.getString("NAME");
            this.address = rs.getString("ADDRESS");
            this.countryISO2 = rs.getString("COUNTRY ISO2 CODE");
            this.countryName = rs.getString("COUNTRY NAME");
            this.isHeadquarter = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
