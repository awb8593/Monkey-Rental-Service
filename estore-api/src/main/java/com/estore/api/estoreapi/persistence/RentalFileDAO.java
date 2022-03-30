package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Rental;

/**
 * Implements the functionality for JSON file-based peristance for Rentals
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Jack Yakubison jcy4561
 */
@Component
public class RentalFileDAO implements RentalDAO{

    private static final Logger LOG = Logger.getLogger(MonkeyFileDAO.class.getName());
    Map<Integer,Rental> rentals;   // Provides a local cache of the rental objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Monkeys
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new rental
    private String filename;    // Filename to read from and write to
    
    /**
     * Creates a Rental File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public RentalFileDAO(@Value("${rentals.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the monkeys from the file
    }

    /**
     * Generates the next id for a new {@linkplain Rental rental}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Rental rentals} from the tree map
     * 
     * @return  The array of {@link Rental rentals}, may be empty
     */
    private Rental[] getRentalsArray() {
        return getRentalsArray(-1, false);
    }

    /**
     * Generates an array of {@linkplain Rental rentals} from the tree map for any
     * {@linkplain Rental rental} that contains the userId specified by userId
     * <br>
     * If userId is -1, the array contains all of the {@linkplain Rental rental}
     * in the tree map
     * 
     * If onlyActive is true the returned array will only include rentals with the active set to true.
     * 
     * @return  The array of {@link Rental rentals}, may be empty
     */
    private Rental[] getRentalsArray(int userId, boolean onlyActive ) { 
        ArrayList<Rental> rentalArrayList = new ArrayList<>();

        for (Rental rental : rentals.values()) {
           
            if( onlyActive && !rental.getActive()) {
                continue;
            }
            if (userId == -1 || rental.getUserId() == userId) {
                rentalArrayList.add(rental);
            }
        }

        Rental[] rentalArray = new Rental[rentalArrayList.size()];
        rentalArrayList.toArray(rentalArray);
        return rentalArray;
    }

    /**
     * Saves the {@linkplain Rental rentals} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Rental rentals} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Rental[] rentalArray = getRentalsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),rentalArray);
        return true;
    }

    /**
     * Loads {@linkplain Rental rentals} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        rentals = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of monkeys
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Rental[] rentalArray = objectMapper.readValue(new File(filename),Rental[].class);

        // Add each rental to the tree map and keep track of the greatest id
        for (Rental rental : rentalArray) {
            rentals.put(rental.getId(), rental);
            if (rental.getId() > nextId)
                nextId = rental.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Rental createRental(Rental rental) throws IOException {
        synchronized(rental) {
            // We create a new rental object because the id field is immutable
            // and we need to assign the next unique id
            Rental newRental = new Rental(nextId(),rental.getRentalDate(), rental.getReturnDate(), rental.getUserId(), rental.getMonkeyId());
            rentals.put(newRental.getId(),newRental);
            save(); // may throw an IOException
            return newRental;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Rental updateRental(Rental rental) throws IOException {
        synchronized(rentals) {
            if (rentals.containsKey(rental.getId()) == false)
                return null;  // rental does not exist

            rentals.put(rental.getId(),rental);
            save(); // may throw an IOException
            return rental;
        }
    }

    /**
    ** {@inheritDoc}
     */
    public Rental getRentalId(int id) {
        synchronized(rentals) {
            if (rentals.containsKey(id)) {
                return rentals.get(id);
            }
            else {
                return null;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    public Rental[] getRentalsUser(int userId, boolean onlyActive) {
        synchronized(rentals) {
            return getRentalsArray(userId, onlyActive);
        }
    }

    /**
    ** {@inheritDoc}
     */
    public Rental[] getAllRentals(boolean onlyActive)
    {
        synchronized(rentals) {
            return getRentalsArray(-1, onlyActive);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteRental(int id) throws IOException {
        synchronized(rentals) {
            if (rentals.containsKey(id)) {
                rentals.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}

