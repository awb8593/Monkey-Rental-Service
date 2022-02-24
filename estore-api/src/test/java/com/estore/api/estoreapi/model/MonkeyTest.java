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
        assertEquals(expected_price,monkey.getPrice());
        assertEquals(expected_species,monkey.getSpecies());
        assertEquals(expected_description,monkey.getDescription());
        assertEquals(expected_rented, monkey.getRented());
        
    }

    @Test
    public void testName() {
        // Setup 
        int expected_id = 99;
        String expected_name = "TestMonkey";
        float expected_price = 90;
        String expected_species = "TestSpecies";
        String expected_description = "TestDescription";

        String testName = "TESTING NAME";
        Monkey monkey = new Monkey(expected_id, testName, expected_price, expected_species, expected_description);

        monkey.setName(expected_name);

        assertEquals(expected_name, monkey.getName());
    }

    @Test
    public void testPrice() {
        // Setup 
        int expected_id = 99;
        String expected_name = "TestMonkey";
        float expected_price = 90;
        String expected_species = "TestSpecies";
        String expected_description = "TestDescription";

        float testPrice = 80;
        Monkey monkey = new Monkey(expected_id, expected_name, testPrice, expected_species, expected_description);

        monkey.setPrice(expected_price);

        assertEquals(expected_price, monkey.getPrice());
    }

    @Test
    public void testSpecies() {
        // Setup 
        int expected_id = 99;
        String expected_name = "TestMonkey";
        float expected_price = 90;
        String expected_species = "TestSpecies";
        String expected_description = "TestDescription";

        String testSpecies = "TESTING SPECIES";
        Monkey monkey = new Monkey(expected_id, expected_name, expected_price, testSpecies, expected_description);

        monkey.setSpecies(expected_species);

        assertEquals(expected_species, monkey.getSpecies());
    }

    @Test
    public void testDescription() {
        // Setup 
        int expected_id = 99;
        String expected_name = "TestMonkey";
        float expected_price = 90;
        String expected_species = "TestSpecies";
        String expected_description = "TestDescription";

        String testDescription = "TESTING DESCRIPTION";
        Monkey monkey = new Monkey(expected_id, expected_name, expected_price, expected_species, testDescription);

        monkey.setDescription(expected_description);

        assertEquals(expected_description, monkey.getDescription());
    }

    @Test
    public void testRented() {
        // Setup 
        int expected_id = 99;
        String expected_name = "TestMonkey";
        float expected_price = 90;
        String expected_species = "TestSpecies";
        String expected_description = "TestDescription";
        boolean expected_rented = true;

        Monkey monkey = new Monkey(expected_id, expected_name, expected_price, expected_species, expected_description);

        monkey.setRented(expected_rented);

        assertEquals(expected_rented, monkey.getRented());
    }

    @Test
    public void testToString() {
        int id = 99;
        String name = "TestMonkey";
        float price = 90;
        String species = "TestSpecies";
        String description = "TestDescription";
        Monkey monkey = new Monkey(id,name,price,species,description);

        String expected_string = String.format(Monkey.STRING_FORMAT,id,name,price,species,description,false);

        String actual_string = monkey.toString();

        assertEquals(expected_string, actual_string);
    
    }

}