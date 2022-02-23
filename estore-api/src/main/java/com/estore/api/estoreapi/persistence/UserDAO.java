package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.User;

/**
 * Defines the interface for User object persistence
 * 
 * @author Jack Yakubison jcy4561
 */
public interface UserDAO {
   
    /**
     * Creates and saves a {@linkplain User user}
     * 
     * @param user {@linkplain User user} object to be created and saved
     * <br>
     * The id of the user object is ignored and a new uniqe id is assigned
     *
     * @return new {@link User user} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User user) throws IOException;


    /**
     * Updates and saves a {@linkplain User user}
     * 
     * @param {@link User user} object to be updated and saved
     * 
     * @return updated {@link User user} if successful, null if
     * {@link User user} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    User updateUser(User user) throws IOException;

    /**
     * Gets the {@linkplain User user} whose id matches the entered id
     * @param id The id to look for
     * @return the {@link User user} whose ID matches the given text may be null if user with id doesnt exist
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getUser(int id) throws IOException;

    
    /**
     * Finds all {@linkplain User user} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link User user} whose names contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] findUsers(String containsText) throws IOException;

    /**
     * Deletes a {@linkplain User user} with the given id
     * 
     * @param id The id of the {@link User user}
     * 
     * @return true if the {@link User user} was deleted
     * <br>
     * false if user with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(int id) throws IOException;
    /**
     * Retrieves all {@linkplain User users}
     * 
     * @return An array of {@link User hero} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getAllUsers() throws IOException;
}
