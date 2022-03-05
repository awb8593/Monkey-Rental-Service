package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.client.ExpectedCount;

/**
 * The unit test suite for the User class
 * 
 * @author Jack Yakubison jcy4561
 */
@Tag("Model-tier")
public class UserTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_username = "TestUser";
        ArrayList<Integer> expected_list = new ArrayList<Integer>();


        // Invoke
        User user = new User(expected_id, expected_username);

        // Analyze
        assertEquals(expected_id,user.getId());
        assertEquals(expected_username,user.getUsername());
        assertEquals(expected_list,user.getCartList());
        assertEquals(expected_list,user.getRentedList());
    }

    @Test
    public void testUsername() {
        // Setup 
        int expected_id = 99;
        String expected_Username = "TestUser";


        String testName = "TESTING NAME";
        User user = new User(expected_id, testName);

        user.setUsername(expected_Username);

        assertEquals(expected_Username, user.getUsername());
    }


    @Test
    public void testCartList() {
        // Setup 
        int expected_id = 99;
        String expected_Username = "TestUser";

        ArrayList<Integer> expected_cartList = new ArrayList<Integer>();
        expected_cartList.add(5);
        
        User user = new User(expected_id, expected_Username);

        user.setCartList(expected_cartList);

        assertEquals(expected_cartList, user.getCartList());
    }


    @Test
    public void testRentedList() {
        // Setup 
        int expected_id = 99;
        String expected_Username = "TestUser";

        ArrayList<Integer> expected_rentedList = new ArrayList<Integer>();
        expected_rentedList.add(5);
        
        User user = new User(expected_id, expected_Username);

        user.setRentedList(expected_rentedList);

        assertEquals(expected_rentedList, user.getRentedList());
    }



    @Test
    public void testToString() {
        int id = 99;
        String username = "TestUser";
        ArrayList<Integer> cartList = new ArrayList<Integer>();
    
        ArrayList<Integer> rentedList = new ArrayList<Integer>();

        User user = new User(id, username);

        String expected_string = String.format(User.STRING_FORMAT,id, username, cartList, rentedList);

        String actual_string = user.toString();

        assertEquals(expected_string, actual_string);
    
    }
}

