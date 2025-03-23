package org.example;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import static spark.Spark.*;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        port(8080);
        Database.connect();
        SwiftCodeService scs = new SwiftCodeService(Database.getConnection());

        get("/", (req, res) -> "SWIFT codes app");

        get("/v1/swift-codes/:swiftCode", (req, res) -> {
            String swiftCode = req.params("swiftCode");
            boolean isHeadquarter = swiftCode.endsWith("XXX");
            res.type("application/json");

            try{
                Object result;
                if(isHeadquarter){
                    result = scs.getHeadquarter(swiftCode);
                }else{
                    result = scs.getBank(swiftCode);
                }
                if(result == null){
                    res.status(404);
                    return "{\"error\": \"SWIFT code not found\"}";
                }
                res.status(200);
                return Mapper.prettyMap(result);

            } catch (Exception e) {
                res.status(500);
                return "{\"error\": \"Internal server error: " + e.getMessage() + "\"}";
            }
        });



        get("/v1/swift-codes/country/:countryISO2", (req, res) -> {
            String countryISO2 = req.params("countryISO2").toUpperCase();
            res.type("application/json");

            try {
                var country = scs.getByCountry(countryISO2);
                if (country == null ) {
                    res.status(404);
                    return "{\"error\": \"No SWIFT codes found for country " + countryISO2 + "\"}";
                }
                return Mapper.prettyMap(country);
            } catch (Exception e) {
                res.status(500);
                return "{\"error\": \"Internal server error: " + e.getMessage() + "\"}";
            }
        });


        post("/v1/swift-codes/", (req, res) -> {
            res.type("application/json");
            if(scs.addSwiftCode(req.body()) == 1)
                return "{\"message\": \"SWIFT Code added successfully\"}";
            res.status(500);
            return "{\"error\": \"Internal server error\"}";
        });

        delete("/v1/swift-codes/:swiftCode", (req, res) -> {
            String swiftCode = req.params("swiftCode");
            int deleted = scs.deleteSwiftCode(swiftCode);
            return "{\"message\": \"" + deleted + " SWIFT Code deleted\"}";
        });
    }
    }




