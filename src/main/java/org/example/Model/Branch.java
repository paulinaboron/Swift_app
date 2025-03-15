package org.example.Model;

public class Branch {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
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

    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public String getSwiftCode() {
        return swiftCode;
    }
}
