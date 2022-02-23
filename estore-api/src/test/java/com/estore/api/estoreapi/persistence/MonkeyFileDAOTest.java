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
 * @author SWEN Faculty
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


}
