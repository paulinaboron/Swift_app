package org.example;
import static spark.Spark.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Model.Branch;
import org.example.Model.Headquarter;
import org.example.Model.SwiftCode;

import java.sql.*;

public class Main {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws SQLException {

        port(8080);
        Database.connect2();

        get("/test", (req, res) -> "Hello, Spark!");

        get("/v1/swift-codes/:swiftCode", (req, res) -> {
            String swiftCode = req.params("swiftCode");
            boolean isHeadquarter = swiftCode.endsWith("XXX");
            res.type("application/json");

            res.status(200);
            if(isHeadquarter){
                Headquarter hq = SwiftCodeService.getHeadquarter(swiftCode);
                if(hq != null){
                    return hq;
                }
            }else{
                return SwiftCodeService.getBranch(swiftCode);
            }
            res.status(404);
            return "{\"message\": \"SWIFT Code not found\"}";
        });

        get("/v1/swift-codes/country/:countryISO2", (req, res) -> {
            String countryISO2 = req.params("countryISO2");
            res.type("application/json");
            return SwiftCodeService.getByCountry(countryISO2);
        });

        post("/v1/swift-codes/", (req, res) -> {
            SwiftCodeService.addSwiftCode(req.body());
            res.type("application/json");
            return "{\"message\": \"SWIFT Code added successfully\"}";
        });

        delete("/v1/swift-codes/:swiftCode", (req, res) -> {
            String swiftCode = req.params("swiftCode");
            SwiftCodeService.deleteSwiftCode(swiftCode);
            return "{\"message\": \"SWIFT Code deleted successfully\"}";
        });
    }
    }


