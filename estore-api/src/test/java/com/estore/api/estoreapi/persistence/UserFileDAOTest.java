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
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the User File DAO class
 * 
 * @author Jack Yakubison
 */
@Tag("Persistence-tier")
public class UserFileDAOTest {
    UserFileDAO userFileDAO;
    User[] testUsers;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupUserFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[3];
        testUsers[0] = new User(98, "TestUser1");
        testUsers[1] = new User(99, "TestUser2");
        testUsers[2] = new User(100, "TestUser3");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the user array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),User[].class))
                .thenReturn(testUsers);
        userFileDAO = new UserFileDAO("doesnt_matter.txt",mockObjectMapper);
    }


    @Test
    public void testCreateUsers() {
        // Setup
        User user = new User(101, "NewTestUser");

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.createUser(user),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        User actual = userFileDAO.getUserId(user.getId());
        assertEquals(actual.getId(),user.getId());
        assertEquals(actual.getUsername(),user.getUsername());
    }


    @Test
    public void testUpdateUser() {
        // Setup
        User user = new User(98, "TestUser1");
        ArrayList<Integer> updatedList = new ArrayList<Integer>();
        updatedList.add(1);
        user.setCartList(updatedList);

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(user),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        User actual = userFileDAO.getUserId(user.getId());
        assertEquals(actual,user);
    }


    @Test
    public void testUpdateUserNotAccepted() {
        // Setup
        User user = new User(98, "TestUserChanged");

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(user),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        User actual = userFileDAO.getUserId(user.getId());
        assertEquals(actual,testUsers[0]);
    }


    @Test
    public void testUpdateUserNotFound() {
        // Setup
        User user = new User(101, "NewUser");

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(user),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }


    @Test
    public void testGetAllUsers() throws IOException {
        // Invoke
        User[] users = userFileDAO.getAllUsers();

        // Analyze
        assertEquals(users.length,testUsers.length);
        for (int i = 0; i < testUsers.length;++i)
            assertEquals(users[i],testUsers[i]);
    }


    @Test
    public void testGetUserName() {
        // Invoke
        User user = userFileDAO.getUserName("TestUser1");

        // Analyze
        assertEquals(user, testUsers[0]);
    }


    @Test
    public void testGetUserNameNotFound(){
        // Invoke
        User user = userFileDAO.getUserName("NotUser");

        // Analyze
        assertNull(user);
    }


    @Test
    public void testGetUserId() {
        // Invoke
        User user = userFileDAO.getUserId(99);

        // Analyze
        assertEquals(user, testUsers[1]);
    }


    @Test
    public void testGetUserIdNotFound(){
        // Invoke
        User user = userFileDAO.getUserId(0);

        // Analyze
        assertNull(user);
    }


    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(User[].class));

        User user = new User(101, "NewUser");

        assertThrows(IOException.class,
                        () -> userFileDAO.createUser(user),
                        "IOException not thrown");
    }


    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the UserFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),User[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new UserFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}

