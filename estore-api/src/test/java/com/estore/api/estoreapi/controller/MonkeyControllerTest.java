package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.MonkeyDAO;
import com.estore.api.estoreapi.model.Monkey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.HeaderAssertions;

/**
 * Test the Monkey Controller class
 * 
 * @author Jack Yakubison jcy4561
 */
@Tag("Controller-tier")
public class MonkeyControllerTest {
    private MonkeyController monkeyController;
    private MonkeyDAO mockMonkeyDAO;

    /**
     * Before each test, create a new MonkeyController object and inject
     * a mock Monkey DAO
     */
    @BeforeEach
    public void setupMonkeyController() {
        mockMonkeyDAO = mock(MonkeyDAO.class);
        monkeyController = new MonkeyController(mockMonkeyDAO);
    }


    @Test
    public void testGetMonkey() throws IOException {
        int id = 99;
        String name = "TestMonkey";
        float price = 90;
        String species = "TestSpecies";
        String description = "TestDescription";
        Monkey monkey = new Monkey(id,name,price,species,description);

        when(mockMonkeyDAO.getMonkey(monkey.getId())).thenReturn(monkey);

        ResponseEntity<Monkey> response = monkeyController.getMonkey(monkey.getId());

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(monkey,response.getBody());
    }

    @Test
    public void testgetMonkeyNotFound() throws Exception {
        int id = 99;

        when(mockMonkeyDAO.getMonkey(id)).thenReturn(null);

        ResponseEntity<Monkey> response = monkeyController.getMonkey(id);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetMonkeyHandleException() throws Exception { 
        // Setup
        int id = 99;
        // When getMonkey is called on the Mock Monkey DAO, throw an IOException
        doThrow(new IOException()).when(mockMonkeyDAO).getMonkey(id);

        // Invoke
        ResponseEntity<Monkey> response = monkeyController.getMonkey(id);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateMonkey() throws IOException {  // createMonkey may throw IOException
        // Setup
        Monkey monkey = new Monkey(99,"TestMonkeyJames", 99, "JAMES SPECIES", "JAMES DESC");
        // when createMonkey is called, return true simulating successful
        // creation and save
        when(mockMonkeyDAO.createMonkey(monkey)).thenReturn(monkey);

        // Invoke
        ResponseEntity<Monkey> response = monkeyController.createMonkey(monkey);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(monkey,response.getBody());
    }

    @Test
    public void testCreateMonkeyFailed() throws IOException {  // createMonkey may throw IOException
        // Setup
        Monkey monkey = new Monkey(99,"", 99, "JAMES SPECIES", "JAMES DESC");
        // when createMonkey is called, return false simulating failed
        // creation and save
        when(mockMonkeyDAO.createMonkey(monkey)).thenReturn(null);

        // Invoke
        ResponseEntity<Monkey> response = monkeyController.createMonkey(monkey);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateMonkeyHandleException() throws IOException {  // createMonkey may throw IOException
        // Setup
        Monkey monkey = new Monkey(99,"TestMonkeyJames", 99, "JAMES SPECIES", "JAMES DESC");

        // When createMonkey is called on the Mock Monkey DAO, throw an IOException
        doThrow(new IOException()).when(mockMonkeyDAO).createMonkey(monkey);

        // Invoke
        ResponseEntity<Monkey> response = monkeyController.createMonkey(monkey);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    public void testUpdateMonkey() throws IOException { // updateMonkey may throw IOException
        // Setup
        Monkey monkey = new Monkey(99,"TestMonkeyJames", 99, "JAMES SPECIES", "JAMES DESC");
        // when updateMonkey is called, return true simulating successful
        // creation and save
        when(mockMonkeyDAO.updateMonkey(monkey)).thenReturn(monkey);

        // Invoke
        ResponseEntity<Monkey> response = monkeyController.createMonkey(monkey);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
    public void testUpdateMonkeyFailed() throws IOException {  // updateMonkey may throw IOException
        // Setup
        Monkey monkey = new Monkey(99,"", 99, "JAMES SPECIES", "JAMES DESC");
        // when updateMonkey is called, return false simulating failed
        // creation and save
        when(mockMonkeyDAO.createMonkey(monkey)).thenReturn(null);

        // Invoke
        ResponseEntity<Monkey> response = monkeyController.updateMonkey(monkey);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    public void testUpdateMonkeyHandleException() throws IOException {  // updateMonkey may throw IOException
        // Setup
        Monkey monkey = new Monkey(99,"TestMonkeyJames", 99, "JAMES SPECIES", "JAMES DESC");

        // When updateMonkey is called on the Mock Monkey DAO, throw an IOException
        doThrow(new IOException()).when(mockMonkeyDAO).updateMonkey(monkey);

        // Invoke
        ResponseEntity<Monkey> response = monkeyController.updateMonkey(monkey);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetAllMonkeys() throws IOException { // getAllMonkeys may throw IOException
        // Setup
        Monkey[] monkeys = new Monkey[2];
        monkeys[0] = new Monkey(99,"TestMonkeyJames", 99, "JAMES SPECIES", "JAMES DESC");
        monkeys[1] = new Monkey (100, "TestMonkeyJoe", 100, "JOE SPECIES", "JOE DESCR");
        // when getAllMonkeys is called return the monkeys created above
        when(mockMonkeyDAO.getAllMonkeys()).thenReturn(monkeys);

        // Invoke
        ResponseEntity<Monkey[]> response = monkeyController.getAllMonkeys();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(monkeys, response.getBody());
    }

    @Test
    public void testGetAllMonkeysHandleException() throws IOException { // getAllMonkeys may throw IOException
        // Setup
        // when getAllMonkeys is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockMonkeyDAO).getAllMonkeys();

        // Invoke
        ResponseEntity<Monkey[]> response = monkeyController.getAllMonkeys();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchMonkeys() throws IOException { // searchMonkeys may throw IOException
        // Setup
        String searchString = "on";
        Monkey[] monkeys = new Monkey[2];
        monkeys[0] = new Monkey(99,"TestMonkeyJames", 99, "JAMES SPECIES", "JAMES DESC");
        monkeys[1] = new Monkey (100, "TestMonkeyJoe", 100, "JOE SPECIES", "JOE DESCR");

        // When searchMonkeys is called with the search string, return the two
        // monkeys above
        when(mockMonkeyDAO.findMonkeys(searchString)).thenReturn(monkeys);

        // Invoke
        ResponseEntity<Monkey[]> response = monkeyController.searchMonkeys(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(monkeys, response.getBody());
    }
    @Test
    public void testSearchMonkeysHandleException() throws IOException { // findMonkeys may throw IOException
        // Setup
        String searchString = "an";
        // When createMonkey is called on the Mock Monkey DAO, throw an IOException
        doThrow(new IOException()).when(mockMonkeyDAO).findMonkeys(searchString);

        // Invoke
        ResponseEntity<Monkey[]> response = monkeyController.searchMonkeys(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteMonkey() throws IOException {
        // Setup
        int monkeyId = 99;
        // when deleteMonkey is called return true, simulating successful deletion
        when(mockMonkeyDAO.deleteMonkey(monkeyId)).thenReturn(true);

        // Invoke
        ResponseEntity<Monkey> response = monkeyController.deleteMonkey(monkeyId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteMonkeyNotFound() throws IOException {
        // Setup
        int monkeyId = 99;
        // when deleteMonkey is called return false, simulating failed deletion
        when(mockMonkeyDAO.deleteMonkey(monkeyId)).thenReturn(false);

        // Invoke
        ResponseEntity<Monkey> response = monkeyController.deleteMonkey(monkeyId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteMonkeyHandleException() throws IOException {
        // Setup
        int monkeyId = 99;
        // When deleteMonkey is called on the Mock Monkey DAO, throw an IOException
        doThrow(new IOException()).when(mockMonkeyDAO).deleteMonkey(monkeyId);

        // Invoke
        ResponseEntity<Monkey> response = monkeyController.deleteMonkey(monkeyId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}