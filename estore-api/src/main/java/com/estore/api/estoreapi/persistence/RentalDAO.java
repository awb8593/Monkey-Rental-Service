package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Rental;

/**
 * Defines the interface for Rental object persistence
 * 
 * @author Jack Yakubison jcy4561
 */
public interface RentalDAO {
   
    /**
     * Creates and saves a {@linkplain Rental Rental}
     * 
     * @param Rental {@linkplain Rental Rental} object to be created and saved
     * <br>
     * The id of the Rental object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Rental Rental} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Rental createRental(Rental Rental) throws IOException;


    /**
     * Updates and saves a {@linkplain Rental Rental}
     * but rejects the update if the Rentalname changes
     * 
     * @param {@link Rental Rental} object to be updated and saved
     * 
     * @return updated {@link Rental Rental} if successful, null if
     * {@link Rental Rental} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Rental updateRental(Rental Rental) throws IOException;

    /**
     * Gets the {@linkplain Rental Rental} whose id matches the entered id
     * @param id The id to look for
     * @return the {@link Rental Rental} whose ID matches the given text may be null if Rental with id doesnt exist
     * 
     * @throws IOException if an issue with underlying storage
     */
    Rental getRentalId(int id) throws IOException;

    
    /**
     * Retrieves all {@linkplain Rental Rentals} from a user 
     * @param userId the User Id to get all the rentals of
     * @param onlyActive whether you want the returned list to include only active rentals
     * 
     * @return An array of {@link Rental Rental} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Rental[] getRentalsUser(int userId, boolean onlyActive) throws IOException;

    /**
     * Retrieves all {@linkplain Rental Rentals}
     * @param onlyActive whether you want the returned list to include only active rentals
     * 
     * @return An array of {@link Rental Rental} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Rental[] getAllRentals(boolean onlyActive) throws IOException;

    /**
     * Deletes a {@linkplain MonRekey Rental} with the given id
     * 
     * @param id The id of the {@link Rental Rental}
     * 
     * @return true if the {@link Rental Rental} was deleted
     * <br>
     * false if Rental with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteRental(int id) throws IOException;    
}

