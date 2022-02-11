package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Monkey;

/**
 * Defines the interface for Monkey object persistence
 * 
 * @author Adrian Burgos awb8593
 * @author Jack Hester jrh3397
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
     * Retrieves all {@linkplain Monkey monkeys}
     * 
     * @return An array of {@link Monkey hero} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Monkey[] getMonkeys() throws IOException;
}
