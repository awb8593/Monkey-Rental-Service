package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Review;

/**
 * Defines the interface for Review object persistence
 * 
 * @author Adrian Burgos awb8593
 */
public interface ReviewDAO {
    
    /**
     * Creates and saves a {@linkplain Review review}
     * 
     * @param review {@linkplain Review review} object to be created and saved
     * <br>
     *
     * @return new {@link Review review} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Review createReview(Review review) throws IOException;

    /**
     * Updates and saves a {@linkplain Review review}
     * 
     * @param {@link Review review} object to be updated and saved
     * 
     * @return updated {@link Review review} if successful, null if
     * {@link Review review} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Review updateReview(Review review) throws IOException;

    /**
     * gets the {@linkplain Review review} assigned to a specified monkey
     * 
     * @param id the id of the monkey whose reviews are being found
     * 
     * @return {@linkplain Review review} if successful, null if one could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Review getReviews(int id) throws IOException;

    /**
     * Deletes a {@linkplain Review review} with the given id
     * 
     * @param id The id of the {@link Review review}
     * 
     * @return true if the {@link Review review} was deleted
     * <br>
     * false if review with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteReview(int id) throws IOException;
}
