package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Rental class
 * 
 * @author Jack Yakubison jcy4561
 */
@Tag("Model-tier")
public class RentalTest {
    @Test
    public void testCtor() {
        // Setup 
        int expected_id = 99;
        String expected_rentalDate = "2000-01-01T00:00:00.000Z";
        String expected_returnDate = "2001-01-01T00:00:00.000Z";
        int expected_userId = 99;
        int expected_monkeyId = 99;
        boolean expected_active = true;
        
        // Invoke
        Rental rental = new Rental(expected_id, expected_rentalDate, expected_returnDate, expected_userId, expected_monkeyId );

        // Analyze
        assertEquals(expected_id,rental.getId());
        assertEquals(expected_rentalDate,rental.getRentalDate());
        assertEquals(expected_returnDate,rental.getReturnDate());
        assertEquals(expected_userId,rental.getUserId());
        assertEquals(expected_monkeyId,rental.getMonkeyId());
        assertEquals(expected_active,rental.getActive());
        
    }

    @Test
    public void testRentalDate() {
        // Setup 
        int expected_id = 99;
        String expected_rentalDate = "2000-01-01T00:00:00.000Z";
        String expected_returnDate = "2001-01-01T00:00:00.000Z";
        int expected_userId = 99;
        int expected_monkeyId = 99;

        String testRentalDate = "TESTING NAME";
        Rental rental = new Rental(expected_id, testRentalDate, expected_returnDate, expected_userId, expected_monkeyId );

        rental.setRentalDate(expected_rentalDate);

        assertEquals(expected_rentalDate, rental.getRentalDate());
    }

    @Test
    public void testReturnDate() {
        // Setup 
        int expected_id = 99;
        String expected_rentalDate = "2000-01-01T00:00:00.000Z";
        String expected_returnDate = "2001-01-01T00:00:00.000Z";
        int expected_userId = 99;
        int expected_monkeyId = 99;

        String testReturnDate = "TESTING NAME";
        Rental rental = new Rental(expected_id, expected_rentalDate, testReturnDate, expected_userId, expected_monkeyId );

        rental.setReturnDate(expected_returnDate);

        assertEquals(expected_returnDate, rental.getReturnDate());
    }

    @Test
    public void testUserId() {
        // Setup 
        int expected_id = 99;
        String expected_rentalDate = "2000-01-01T00:00:00.000Z";
        String expected_returnDate = "2001-01-01T00:00:00.000Z";
        int expected_userId = 99;
        int expected_monkeyId = 99;

        int testUserId = 1;
        Rental rental = new Rental(expected_id, expected_rentalDate, expected_returnDate, testUserId, expected_monkeyId );

        rental.setUserId(expected_userId);

        assertEquals(expected_userId, rental.getUserId());
    }

    @Test
    public void testMonkeyId() {
        // Setup 
        int expected_id = 99;
        String expected_rentalDate = "2000-01-01T00:00:00.000Z";
        String expected_returnDate = "2001-01-01T00:00:00.000Z";
        int expected_userId = 99;
        int expected_monkeyId = 99;

        int testMonkeyId = 1;
        Rental rental = new Rental(expected_id, expected_rentalDate, expected_returnDate, expected_userId, testMonkeyId );

        rental.setMonkeyId(expected_monkeyId);

        assertEquals(expected_monkeyId, rental.getMonkeyId());
    }

    @Test
    public void testActive() {
        // Setup 
        int expected_id = 99;
        String expected_rentalDate = "2000-01-01T00:00:00.000Z";
        String expected_returnDate = "2001-01-01T00:00:00.000Z";
        int expected_userId = 99;
        int expected_monkeyId = 99;
        boolean expected_active = false;

        Rental rental = new Rental(expected_id, expected_rentalDate, expected_returnDate, expected_userId, expected_monkeyId );

        rental.setActive(expected_active);

        assertEquals(expected_active, rental.getActive());
    }

    @Test
    public void testToString() {
        int expected_id = 99;
        String expected_rentalDate = "2000-01-01T00:00:00.000Z";
        String expected_returnDate = "2001-01-01T00:00:00.000Z";
        int expected_userId = 99;
        int expected_monkeyId = 99;
        boolean expected_active = true;
        Rental rental = new Rental(expected_id, expected_rentalDate, expected_returnDate, expected_userId, expected_monkeyId);

        String expected_string = String.format(Rental.STRING_FORMAT,expected_id,expected_rentalDate,expected_returnDate,expected_userId,expected_monkeyId,expected_active);

        String actual_string = rental.toString();

        assertEquals(expected_string, actual_string);
    }
}

