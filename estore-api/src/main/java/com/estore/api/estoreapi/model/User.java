package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a User entity
 * 
 * @author Jack Yakubison jcy4561
 */
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "User [id=%d, username=%s, cartList=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("cartList") private ArrayList<Integer> cartList;
    @JsonProperty("rentedList") private ArrayList<Integer> rentedList;

    /**
     * Create a user with the given id and name
     * @param id The id of the user
     * @param username The username of the user
     * 
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(@JsonProperty("id") int id,
        @JsonProperty("username") String username) {
        this.id = id;
        this.username = username;
        this.cartList = new ArrayList<Integer>();
        this.rentedList = new ArrayList<Integer>();

    }

    /**
     * Retrieves the id of the user
     * @return The id of the user
     */
    public int getId() {return id;}

    /**
     * Sets the username of the user - necessary for JSON object to Java object deserialization
     * @param username The username of the user
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Retrieves the username of the user
     * @return The username of the user
     */
    public String getUsername() {return username;}

    
    /**
     * Sets the cartList of the user - necessary for JSON object to Java object deserialization
     * @param cartList The cartList of the user
     */
    public void setCartList(ArrayList<Integer> cartList) {this.cartList = cartList;}

    /**
     * Retrieves the cartList of the user
     * @return The cartList of the user
     */
    public ArrayList<Integer> getCartList() {return cartList;}


    /**
     * Sets the rentedList of the user - necessary for JSON object to Java object deserialization
     * @param rentedList The cartList of the user
     */
    public void setRentedList(ArrayList<Integer> rentedList) {this.rentedList= rentedList;}

    /**
     * Retrieves the rentedList of the user
     * @return The rentedList of the user
     */
    public ArrayList<Integer> getRentedList() {return rentedList;}
    


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,username,cartList,rentedList);
    }
}

