package org.example.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Country {
    private String countryISO2;
    private String countryName;
    private List<Branch> branches;

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

}
