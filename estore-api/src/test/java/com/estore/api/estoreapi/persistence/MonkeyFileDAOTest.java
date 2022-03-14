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
import com.estore.api.estoreapi.model.Monkey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Monkey File DAO class
 * 
 */
@Tag("Persistence-tier")
public class MonkeyFileDAOTest {
    MonkeyFileDAO monkeyFileDAO;
    Monkey[] testMonkeys;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupMonkeyFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testMonkeys = new Monkey[3];
        testMonkeys[0] = new Monkey(99,"TestMonkeyJames", 99, "JAMES SPECIES", "JAMES DESC" );
        testMonkeys[1] = new Monkey(100,"TestMonkeyAlphred", 85, "ALPHRED SPECIES", "ALPHRED DESC");
        testMonkeys[2] = new Monkey(101,"TestMonkeyBeth", 102, "BETH SPECIES", "BETH DESC");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the monkey array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Monkey[].class))
                .thenReturn(testMonkeys);
        monkeyFileDAO = new MonkeyFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testDeleteMonkey() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> monkeyFileDAO.deleteMonkey(99), "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test monkeys array - 1 (because of the delete)
        // Because monkeys attribute of MonkeyFileDAO is package private
        // we can access it directly
        assertEquals(monkeyFileDAO.monkeys.size(),testMonkeys.length-1);
    }

    @Test
    public void testCreateMonkey() {
        // Setup
        Monkey monkey = new Monkey(102 ,"TestMonkeyDonkeyKong", 1000, "KONG", "Specializes in video game-themed parties" );

        // Invoke
        Monkey result = assertDoesNotThrow(() -> monkeyFileDAO.createMonkey(monkey),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Monkey actual = monkeyFileDAO.getMonkey(monkey.getId());
        assertEquals(actual.getId(),monkey.getId());
        assertEquals(actual.getName(),monkey.getName());
    }

    @Test
    public void testUpdateMonkey() {
        // Setup
        Monkey monkey = new Monkey(99,"TestMonkeyJimbo", 99, "JIMBO SPECIES", "JIMBO DESC" );

        // Invoke
        Monkey result = assertDoesNotThrow(() -> monkeyFileDAO.updateMonkey(monkey),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Monkey actual = monkeyFileDAO.getMonkey(monkey.getId());
        assertEquals(actual,monkey);
    }

    @Test
    public void testGetAllMonkeys() throws IOException {
        // Invoke
        Monkey[] monkeys = monkeyFileDAO.getAllMonkeys();

        // Analyze
        assertEquals(monkeys.length,testMonkeys.length);
        for (int i = 0; i < testMonkeys.length;++i)
            assertEquals(monkeys[i],testMonkeys[i]);
    }

    @Test
    public void testFindMonkeys() {
        // Invoke
        Monkey[] monkeys = monkeyFileDAO.findMonkeys("h");

        // Analyze
        assertEquals(monkeys.length,2);
        assertEquals(monkeys[0], testMonkeys[1]);
        assertEquals(monkeys[1], testMonkeys[2]);
    }

    @Test
    public void testGetMonkey() {
        // Invoke
        Monkey monkey = monkeyFileDAO.getMonkey(99);

        // Analyze
        assertEquals(monkey, testMonkeys[0]);
    }

    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Monkey[].class));

        Monkey monkey = new Monkey(121,"Sugriva", 42, "Chimpanzee", "Loves his dinner");

        assertThrows(IOException.class,
                        () -> monkeyFileDAO.createMonkey(monkey),
                        "IOException not thrown");
    }

    @Test
    public void testGetMonkeyNotFound() {
        // Invoke
        Monkey monkey = monkeyFileDAO.getMonkey(98);

        // Analyze
        assertEquals(monkey,null);
    }

    @Test
    public void testDeleteMonkeyNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> monkeyFileDAO.deleteMonkey(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(monkeyFileDAO.monkeys.size(),testMonkeys.length);
    }

    @Test
    public void testUpdateMonkeyNotFound() {
        // Setup
        Monkey monkey = new Monkey(98,"Ceasar", 33, "Chimpanzee", "Has big plans");

        // Invoke
        Monkey result = assertDoesNotThrow(() -> monkeyFileDAO.updateMonkey(monkey),
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
        // from the MonkeyFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Monkey[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new MonkeyFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
