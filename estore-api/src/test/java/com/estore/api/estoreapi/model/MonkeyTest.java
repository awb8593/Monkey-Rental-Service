package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Monkey class
 * 
 * @author Jack Yakubison jcy4561
 */
@Tag("Model-tier")
public class MonkeyTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "TestMonkey";
        float expected_price = 90;
        String expected_species = "TestSpecies";
        String expected_description = "TestDescription";
        boolean expected_rented = false;
        


        // Invoke
        Monkey monkey = new Monkey(expected_id,expected_name, expected_price, expected_species, expected_description );

        // Analyze
        assertEquals(expected_id,monkey.getId());
        assertEquals(expected_name,monkey.getName());
    }
}