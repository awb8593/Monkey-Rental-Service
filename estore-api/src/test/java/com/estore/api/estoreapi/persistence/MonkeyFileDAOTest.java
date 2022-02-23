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
    MonkeyFileDAO heroFileDAO;
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
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Monkey[].class))
                .thenReturn(testMonkeys);
        heroFileDAO = new MonkeyFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

}
