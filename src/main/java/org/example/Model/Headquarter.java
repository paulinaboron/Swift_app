package org.example.Model;

import java.util.List;

public class Headquarter {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private String swiftCode;
    private List<Branch> branches;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n")
                .append("  address: ").append(address).append("\n")
                .append("  bankName: ").append(bankName).append("\n")
                .append("  countryISO2: ").append(countryISO2).append("\n")
                .append("  countryName: ").append(countryName).append("\n")
                .append("  isHeadquarter: ").append(isHeadquarter).append("\n")
                .append("  swiftCode: ").append(swiftCode).append("\n");

        if (branches != null && !branches.isEmpty()) {
            sb.append("  branches: \n");
            for (Branch branch : branches) {
                sb.append("    - ").append(branch.toString_short()).append("\n");
            }
        } else {
            sb.append("  Branches: None\n");
        }

        sb.append("}");
        return sb.toString();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}
