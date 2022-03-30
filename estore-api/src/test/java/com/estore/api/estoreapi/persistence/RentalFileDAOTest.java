package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Rental File DAO class
 * 
 * @author Adrian Burgos awb8593
 */
@Tag("Persistence-tier")
public class RentalFileDAOTest {
    RentalFileDAO rentalFileDAO;
    Rental[] testRentals;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupRentalFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testRentals = new Rental[3];
        testRentals[0] = new Rental(5, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 4, 5);
        testRentals[1] = new Rental(6, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 6, 7);
        testRentals[2] = new Rental(7, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 8, 9);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the rental array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Rental[].class))
                .thenReturn(testRentals);
        rentalFileDAO = new RentalFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testDeleteRental() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> rentalFileDAO.deleteRental(5), "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test rentals array - 1 (because of the delete)
        // Because rentals attribute of RentalFileDAO is package private
        // we can access it directly
        assertEquals(rentalFileDAO.rentals.size(),testRentals.length-1);
    }

    @Test
    public void testCreateRental() {
        // Setup
        Rental rental = new Rental (8, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 10, 11);

        // Invoke
        Rental result = assertDoesNotThrow(() -> rentalFileDAO.createRental(rental),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Rental actual = rentalFileDAO.getRentalId(rental.getId());
        assertEquals(actual.getId(),rental.getId());
    }

    @Test
    public void testUpdateRental() {
        // Setup
        Rental rental = new Rental(5, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 10, 11);

        // Invoke
        Rental result = assertDoesNotThrow(() -> rentalFileDAO.updateRental(rental),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Rental actual = rentalFileDAO.getRentalId(rental.getId());
        assertEquals(actual,rental);
    }

    @Test
    public void testGetAllRentals() throws IOException {
        // Invoke
        Rental[] rentals = rentalFileDAO.getAllRentals(true);

        // Analyze
        assertEquals(rentals.length,testRentals.length);
        for (int i = 0; i < testRentals.length;++i)
            assertEquals(rentals[i],testRentals[i]);
    }

    @Test
    public void testGetRentalId() {
        // Invoke
        Rental rental = rentalFileDAO.getRentalId(5);

        // Analyze
        assertEquals(rental, testRentals[0]);
    }

    @Test
    public void testGetRentalsUser() {
        // Invoke
        Rental[] rentals = rentalFileDAO.getRentalsUser(4, true);

        Rental[] rentalsTestList = new Rental[1];
        rentalsTestList[0] = testRentals[0];
        // Analyze
        assertEquals(rentals[0],rentalsTestList[0]);
    }

    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Rental[].class));

        Rental rental = new Rental(9, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 10, 11);

        assertThrows(IOException.class,
                        () -> rentalFileDAO.createRental(rental),
                        "IOException not thrown");
    }

    @Test
    public void testGetRentalNotFound() {
        // Invoke
        Rental rental = rentalFileDAO.getRentalId(98);

        // Analyze
        assertEquals(rental,null);
    }

    @Test
    public void testDeleteRentalNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> rentalFileDAO.deleteRental(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(rentalFileDAO.rentals.size(),testRentals.length);
    }

    @Test
    public void testUpdateRentalNotFound() {
        // Setup
        Rental rental = new Rental(9, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 10, 11);

        // Invoke
        Rental result = assertDoesNotThrow(() -> rentalFileDAO.updateRental(rental),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the RentalFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Rental[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new RentalFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
