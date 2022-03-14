package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.HeaderAssertions;

/**
 * Test the User Controller class
 * 
 * @author Jack Yakubison jcy4561
 */
@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;

    /**
     * Before each test, create a new UserController object and inject
     * a mock User DAO
     */
    @BeforeEach
    public void setupUserController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    @Test
    public void testCreateUser() throws IOException {  // createUser may throw IOException
        // Setup
        User user = new User(4, "TestUser");
        // when createUser is called, return true simulating successful
        // creation and save
        when(mockUserDAO.createUser(user)).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(user,response.getBody());
    }


    @Test
    public void testCreateUserConflict() throws IOException {  // createUser may throw IOException
        // Setup
        User preUser = new User(3, "TestUser");
        User user = new User(4, "TestUser");

        User[] userList = new User[1];
        userList[0] = preUser;
        // when createUser is called, return a conflict 
        when(mockUserDAO.createUser(user)).thenReturn(user);
        when(mockUserDAO.getAllUsers()).thenReturn(userList);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }


    @Test
    public void testCreateUserHandleException() throws IOException {  // createUser may throw IOException
        // Setup
        User user = new User(4, "TestUser");

        // When createUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).createUser(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    @Test
    public void testUpdateUser() throws IOException {  // updateUser may throw IOException
        // Setup
        User user = new User(3, "TestUser");
        ArrayList<Integer> rentedList = new ArrayList<Integer>();
        rentedList.add(1);
        rentedList.add(3);

        user.setRentedList(rentedList);

        // when updateUser is called, return the user 
        when(mockUserDAO.updateUser(user)).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user, response.getBody());
    }


    @Test
    public void testUpdateUserNotAcceptable() throws IOException {  // updateUser may throw IOException
        // Setup
        User preUser = new User(3, "TestUser");
        User user = new User(3, "TestUser2");
        ArrayList<Integer> rentedList = new ArrayList<Integer>();
        rentedList.add(1);
        rentedList.add(3);

        user.setRentedList(rentedList);

        // when updateUser is called, return the unchanged user to
        // to simulate an unaccepted update
        when(mockUserDAO.updateUser(user)).thenReturn(preUser);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.NOT_ACCEPTABLE,response.getStatusCode());
        assertEquals(preUser, response.getBody());
    }


    @Test
    public void testUpdateUserNotFound() throws IOException {  // updateUser may throw IOException
        // Setup
        User user = new User(3, "TestUser");
        ArrayList<Integer> rentedList = new ArrayList<Integer>();
        rentedList.add(1);
        rentedList.add(3);

        user.setRentedList(rentedList);

        // when updateUser is called, return null
        when(mockUserDAO.updateUser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    
    @Test
    public void testUpdateUserHandleException() throws IOException {  // updateUser may throw IOException
        // Setup
        User user = new User(4, "TestUser");

        // When updateUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).updateUser(user);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    @Test
    public void testGetUserById() throws IOException {  // getUserById may throw IOException
        // Setup
        User user = new User(3, "TestUser");

        // when getUserById is called, return the user
        when(mockUserDAO.getUserId(3)).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getUserById(3);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user, response.getBody());
    }


    @Test
    public void testGetUserByIdNotFound() throws IOException {  // getUserById may throw IOException

        // when getUserById is called, return null
        when(mockUserDAO.getUserId(3)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.getUserById(3);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }


    @Test
    public void testGetUserByIdHandleException() throws IOException {  // getUserById may throw IOException

        // When getUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUserId(4);

        // Invoke
        ResponseEntity<User> response = userController.getUserById(4);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    @Test
    public void testGetUserByName() throws IOException {  // getUserByName may throw IOException
        // Setup
        User user = new User(3, "TestUser");

        // when getUserByName is called, return the user
        when(mockUserDAO.getUserName("TestUser")).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getUserByName("TestUser");

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user, response.getBody());
    }


    @Test
    public void testGetUserByNameNotFound() throws IOException {  // getUserByName may throw IOException
        // when getUserByName is called, return null
        when(mockUserDAO.getUserName("TestUser")).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.getUserByName("TestUser");

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }


    @Test
    public void testGetUserByNameUserHandleException() throws IOException {  // getUserByName may throw IOException

        // When getUserByName is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUserName("TestUser");

        // Invoke
        ResponseEntity<User> response = userController.getUserByName("TestUser");

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    @Test
    public void testGetAllUsers() throws IOException { // getAllUsers may throw IOException
        // Setup
        User[] users = new User[2];
        users[0] = new User(3, "TestUser1");
        users[1] = new User(4, "TestUser2");
        // when getAllUsers is called return the users created above
        when(mockUserDAO.getAllUsers()).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.getAllUsers();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }


    @Test
    public void testGetAllUsersHandleException() throws IOException { // getAllUsers may throw IOException
        // when getAllUsers is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getAllUsers();

        // Invoke
        ResponseEntity<User[]> response = userController.getAllUsers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

