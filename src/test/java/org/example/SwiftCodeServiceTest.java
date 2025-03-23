package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.Model.Bank;
import org.example.Model.Branch;
import org.example.Model.Headquarter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SwiftCodeServiceTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private SwiftCodeService service;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        service = new SwiftCodeService(mockConnection);
    }

    @Test
    void testGetHeadquarter_NotFound() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        Headquarter result = service.getHeadquarter("UNKNOWNXXX");
        assertNull(result);
    }

    @Test
    void testGetBranchesList_ReturnsEmptyList() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);

        List<Branch> result = service.getBranchesList("UNKNOWNXXX");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testGetBank_Found() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("SWIFT CODE")).thenReturn("TPEOPLPWPOD");
        when(mockResultSet.getString("NAME")).thenReturn("PEKAO TOWARZYSTWO FUNDUSZY INWESTYCYJNYCH SPOLKA AKCYJNA");
        when(mockResultSet.getString("ADDRESS")).thenReturn("FOREST ZUBRA 1, FLOOR 1 WARSZAWA, MAZOWIECKIE, 01-066");
        when(mockResultSet.getString("COUNTRY ISO2 CODE")).thenReturn("PL");
        when(mockResultSet.getString("COUNTRY NAME")).thenReturn("POLAND");

        Bank result = service.getBank("TPEOPLPWPOD");

        assertNotNull(result);
        assertEquals("TPEOPLPWPOD", result.getSwiftCode());
        assertEquals("POLAND", result.getCountryName());
    }

    @Test
    void testAddSwiftCode() throws SQLException, JsonProcessingException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        String jsonBody = "{\"address\": \"Test Address\", \"bankName\": \"Test Bank\", \"countryISO2\": \"US\", \"countryName\": \"United States\", \"swiftCode\": \"USBKUS33XXX\"}";

        int result = service.addSwiftCode(jsonBody);

        assertEquals(1, result);
    }

    @Test
    void testDeleteSwiftCode() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        int result = service.deleteSwiftCode("IDXOLV22TIP");
        assertEquals(1, result);
    }
}