package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.persistence.RentalDAO;
import com.estore.api.estoreapi.persistence.MonkeyDAO;
import com.estore.api.estoreapi.model.Rental;
import com.estore.api.estoreapi.model.Monkey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.HeaderAssertions;

/**
 * Test the Rental Controller class
 * 
 * @author Jack Yakubison jcy4561
 */
@Tag("Controller-tier")
public class RentalControllerTest {
    private RentalController rentalController;
    private RentalDAO mockRentalDAO;
    private MonkeyDAO mockMonkeyDAO;

    /**
     * Before each test, create a new RentalController object and inject
     * a mock Rental DAO
     */
    @BeforeEach
    public void setupRentalController() {
        mockRentalDAO = mock(RentalDAO.class);
        mockMonkeyDAO = mock(MonkeyDAO.class);
        rentalController = new RentalController(mockRentalDAO, mockMonkeyDAO);
    }

    @Test
    public void testCreateRental() throws IOException {  // createRental may throw IOException
        // Setup
        Rental rental = new Rental(5, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 4, 4);
        Monkey testMonkey = new Monkey(4, "Test", 10, "", "");
        // when createRental is called, return true simulating successful
        // creation and save
        when(mockRentalDAO.createRental(rental)).thenReturn(rental);
        when(mockMonkeyDAO.getMonkey(4)).thenReturn(testMonkey);

        // Invoke
        ResponseEntity<Rental> response = rentalController.createRental(rental);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(rental,response.getBody());
    }

    @Test
    public void testCreateRentalHandleException() throws IOException {  // createRental may throw IOException
        // Setup
        Rental rental = new Rental(5, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 4, 4);
        Monkey testMonkey = new Monkey(4, "Test", 10, "", "");

        when(mockMonkeyDAO.getMonkey(4)).thenReturn(testMonkey);
        // When createRental is called on the Mock Rental DAO, throw an IOException
        doThrow(new IOException()).when(mockRentalDAO).createRental(rental);

        // Invoke
        ResponseEntity<Rental> response = rentalController.createRental(rental);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    @Test
    public void testUpdateRental() throws IOException {  // updateRental may throw IOException
        // Setup
        Rental rental = new Rental(5, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 4, 4);
        rental.setActive(false);

        // when updateRental is called, return the rental 
        when(mockRentalDAO.updateRental(rental)).thenReturn(rental);

        // Invoke
        ResponseEntity<Rental> response = rentalController.updateRental(rental);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(rental, response.getBody());
    }


    @Test
    public void testUpdateRentalNotFound() throws IOException {  // updateRental may throw IOException
        // Setup
        Rental rental = new Rental(5, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 4, 4);
        rental.setActive(false);

        // when updateRental is called, return null
        when(mockRentalDAO.updateRental(rental)).thenReturn(null);

        // Invoke
        ResponseEntity<Rental> response = rentalController.updateRental(rental);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    
    @Test
    public void testUpdateRentalHandleException() throws IOException {  // updateRental may throw IOException
        // Setup
        Rental rental = new Rental(5, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 4, 4);

        // When updateRental is called on the Mock Rental DAO, throw an IOException
        doThrow(new IOException()).when(mockRentalDAO).updateRental(rental);

        // Invoke
        ResponseEntity<Rental> response = rentalController.updateRental(rental);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    @Test
    public void testGetRental() throws IOException {  // getRentalById may throw IOException
        // Setup
        Rental rental = new Rental(5, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 4, 4);

        // when getRental is called, return the rental
        when(mockRentalDAO.getRentalId(5)).thenReturn(rental);

        // Invoke
        ResponseEntity<Rental> response = rentalController.getRental(5);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(rental, response.getBody());
    }


    @Test
    public void testGetRentalNotFound() throws IOException {  // getRental may throw IOException

        // when getRental is called, return null
        when(mockRentalDAO.getRentalId(3)).thenReturn(null);

        // Invoke
        ResponseEntity<Rental> response = rentalController.getRental(5);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }


    @Test
    public void testGetRentalHandleException() throws IOException {  // getRental may throw IOException

        // When getRental is called on the Mock Rental DAO, throw an IOException
        doThrow(new IOException()).when(mockRentalDAO).getRentalId(5);

        // Invoke
        ResponseEntity<Rental> response = rentalController.getRental(5);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    @Test
    public void testGetRentalsUser() throws IOException {  // getRentalsUser may throw IOException
        // Setup
        Rental[] rentalList =new Rental[2];
        rentalList[0] = new Rental(5, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 4, 4);
        rentalList[1] = new Rental(6, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 4, 5);

        // when getRentalByName is called, return the rental
        when(mockRentalDAO.getRentalsUser(4, true)).thenReturn(rentalList);

        // Invoke
        ResponseEntity<Rental[]> response = rentalController.getRentalsUser(4, true);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(rentalList, response.getBody());
    }


    @Test
    public void testGetRentalsUserRentalHandleException() throws IOException {  // getRentalsUser may throw IOException

        // When getRentalsUser is called on the Mock Rental DAO, throw an IOException
        doThrow(new IOException()).when(mockRentalDAO).getRentalsUser(4, true);

        // Invoke
        ResponseEntity<Rental[]> response = rentalController.getRentalsUser(4, true);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteRental() throws IOException {
        // Setup
        int rentalId = 99;
        // when deleteRental is called return true, simulating successful deletion
        when(mockRentalDAO.deleteRental(rentalId)).thenReturn(true);

        // Invoke
        ResponseEntity<Rental> response = rentalController.deleteRental(rentalId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteRentalNotFound() throws IOException {
        // Setup
        int rentalId = 99;
        // when deleteRental is called return false, simulating failed deletion
        when(mockRentalDAO.deleteRental(rentalId)).thenReturn(false);

        // Invoke
        ResponseEntity<Rental> response = rentalController.deleteRental(rentalId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteRentalHandleException() throws IOException {
        // Setup
        int rentalId = 99;
        // When deleteRental is called on the Mock Rental DAO, throw an IOException
        doThrow(new IOException()).when(mockRentalDAO).deleteRental(rentalId);

        // Invoke
        ResponseEntity<Rental> response = rentalController.deleteRental(rentalId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetAllRentals() throws IOException { // getAllRentals may throw IOException
        // Setup
        Rental[] rentals = new Rental[2];
        rentals[0] = new Rental(5, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 4, 4);
        rentals[1] = new Rental(6, "2000-01-01T00:00:00.000Z", "2001-01-01T00:00:00.000Z", 4, 4);
        // when getAllRentals is called return the rentals created above
        when(mockRentalDAO.getAllRentals(true)).thenReturn(rentals);

        // Invoke
        ResponseEntity<Rental[]> response = rentalController.getAllRentals(true);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rentals, response.getBody());
    }


    @Test
    public void testGetAllRentalsHandleException() throws IOException { // getAllRentals may throw IOException
        // when getAllRentals is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockRentalDAO).getAllRentals(true);

        // Invoke
        ResponseEntity<Rental[]> response = rentalController.getAllRentals(true);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

