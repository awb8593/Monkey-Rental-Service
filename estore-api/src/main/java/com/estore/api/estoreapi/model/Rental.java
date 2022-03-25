package com.estore.api.estoreapi.model;

import java.util.Date;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Rental entity
 * 
 * @author Jack Yakubison jcy4561
 */
public class Rental {
    private static final Logger LOG = Logger.getLogger(Rental.class.getName());

    @JsonProperty("id") private int id;
    @JsonProperty("rentalDate") Date rentalDate;
    @JsonProperty("returnDate") Date returnDate;
    @JsonProperty("userId") int userId;
    @JsonProperty("monkeyId") int monkeyId;
    @JsonProperty("active") boolean active;

    // Package private for tests
    static final String STRING_FORMAT = "Rental [id=%d, rentalDate=%t, returnDate=%t, userId=%d, monkeyId=%d, active=%b]";
    
    /**
     * Create a rental with the given id, rentalDate, returnDate, userId, and monkeyId
     * @param id The id of the rental
     * @param rentalDate The date the monkey was rented
     * @param returnDate The date the monkey should be returned 
     * @param userId The id of the user who rented the monkey
     * @param monkeyId the id of the monkey who is being rented out
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Rental(@JsonProperty("id") int id,
        @JsonProperty("rentalDate") Date rentalDate, 
        @JsonProperty("returnDate") Date returnDate, 
        @JsonProperty("userId") int userId, 
        @JsonProperty("monkeyId") int monkeyId) {
        
        this.id = id;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.userId = userId;
        this.monkeyId = monkeyId;

        this.active = true;

    }

    /**
     * Retrieves the id of the rental
     * @return The id of the rental
     */
    public int getId() {return id;}

    /**
     * Sets the date of the rental - necessary for JSON object to Java object deserialization
     * @param rentalDate The date of the rental
     */
    public void setRentalDate(Date rentalDate) {this.rentalDate = rentalDate;}

    /**
     * Retrieves the date of the rental
     * @return The date of the rental
     */
    public Date getRentalDate() {return rentalDate;}

    /**
     * Sets the date of the rental return - necessary for JSON object to Java object deserialization
     * @param returnDate The expected date of the rental return
     */
    public void setReturnDate(Date returnDate) {this.returnDate = returnDate;}

    /**
     * Retrieves the date of the rental return
     * @return The date of the rental return
     */
    public Date getReturnDate() {return returnDate;}

    /**
     * Sets the userId - necessary for JSON object to Java object deserialization
     * @param userId The id of the user
     */
    public void setUserId(int userId) {this.userId = userId;}

    /**
     * Retrieves the id of the user
     * @return The id of the user 
     */
    public int getUserId() {return userId;}

    /**
     * Sets the MonkeyId - necessary for JSON object to Java object deserialization
     * @param monkeyId The id of the Monkey
     */
    public void setMonkeyId(int monkeyId) {this.monkeyId = monkeyId;}

    /**
     * Retrieves the id of the monkey
     * @return The id of the monkey 
     */
    public int getMonkeyId() {return monkeyId;}

    /**
     * Sets the active status of the rental - necessary for JSON object to Java object deserialization
     * @param active The active status of the rental
     */
    public void setActive(boolean active) {this.active = active;}

    /**
     * Retrieves the active status of the rental
     * @return The active status of the rental
     */
    public boolean getActive() {return active;}

    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,rentalDate,returnDate,userId,monkeyId,active);
    }
}

