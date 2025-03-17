package org.example.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Country {
    private String countryISO2;
    private String countryName;
    private List<Branch> branches;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n")
                .append("  countryISO2: ").append(countryISO2).append("\n")
                .append("  countryName: ").append(countryName).append("\n");

        if (branches != null && !branches.isEmpty()) {
            sb.append("  swiftCodes: \n");
            for (Branch branch : branches) {
                sb.append("    - ").append(branch.toString_short()).append("\n");
            }
        } else {
            sb.append("  swiftCodes: None\n");
        }

        sb.append("}");
        return sb.toString();
    }

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
