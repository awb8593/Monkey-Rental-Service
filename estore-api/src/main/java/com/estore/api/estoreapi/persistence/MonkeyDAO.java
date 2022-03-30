package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Monkey;

/**
 * Defines the interface for Monkey object persistence
 * 
 * @author Adrian Burgos awb8593
 * @author Jack Hester jrh3397
 * @author ELigh Ros edr5068
 */
public interface MonkeyDAO {
   
    /**
     * Creates and saves a {@linkplain Monkey monkey}
     * 
     * @param monkey {@linkplain Monkey monkey} object to be created and saved
     * <br>
     * The id of the monkey object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Monkey monkey} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Monkey createMonkey(Monkey monkey) throws IOException;

    /**
     * Updates and saves a {@linkplain Monkey monkey}
     * 
     * @param {@link Monkey monkey} object to be updated and saved
     * 
     * @return updated {@link Monkey monkey} if successful, null if
     * {@link Monkey monkey} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Monkey updateMonkey(Monkey monkey) throws IOException;

    Monkey getMonkey(int id) throws IOException;

    
    /**
     * Finds all {@linkplain Monkey monkey} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Monkey monkey} whose names contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Monkey[] findMonkeys(String containsText) throws IOException;

    /**
     * Deletes a {@linkplain Monkey monkey} with the given id
     * 
     * @param id The id of the {@link Monkey monkey}
     * 
     * @return true if the {@link Monkey monkey} was deleted
     * <br>
     * false if monkey with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteMonkey(int id) throws IOException;

    /**
     * Retrieves all {@linkplain Monkey monkeys}
     * 
     * @return An array of {@link Monkey monkey} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Monkey[] getAllMonkeys() throws IOException;
}
