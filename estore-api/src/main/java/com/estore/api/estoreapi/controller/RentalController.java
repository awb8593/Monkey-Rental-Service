package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerResponse.SseBuilder;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Rental;
import com.estore.api.estoreapi.persistence.RentalDAO;
import com.estore.api.estoreapi.persistence.MonkeyDAO;

/**
 * Handles the REST API requests for the Rental
 * 
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Jack Yakubison
 */
@RestController
@RequestMapping("rentals")
public class RentalController {
    private static final Logger LOG = Logger.getLogger(RentalController.class.getName());
    private RentalDAO rentalDao;
    private MonkeyDAO monkeyDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param rentalDao The {@link RentalDAO Rental Data Access Object} to perform CRUD operations
     * @param monkeyDao The {@link MonkeyDAO Monkey Data Access Object} to perform CRUD operations
     * 
     * These dependency is injected by the Spring Framework
     */
    public RentalController(RentalDAO rentalDao, MonkeyDAO monkeyDao) {
        this.rentalDao = rentalDao;
        this.monkeyDao = monkeyDao;
    }
    
    /**
     * Creates a {@linkplain Rental rental} with the provided rental object
     * 
     * @param rental - The {@link Rental rental} to create
     * 
     * @return ResponseEntity with created {@link Rental rental} object and HTTP status of CREATED
     * ResponseEntity with HTTP status of CONFLICT if {@link Rental rental} is set to rent a monkey that's rented is true
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
        LOG.info("POST /rentals " + rental);
        boolean monkeyAlreadyRented = false;
        int monkeyId = rental.getMonkeyId();

        try {
            monkeyAlreadyRented = monkeyDao.getMonkey(monkeyId).getRented();
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {   
            Rental newRental = rentalDao.createRental(rental);
            return new ResponseEntity<Rental>(newRental, HttpStatus.CREATED);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Rental rental} with the provided {@linkplain Rental rental} object, if it exists
     * 
     * @param rental The {@link Rental rental} to update
     * 
     * @return ResponseEntity with updated {@link Rental rental} object and HTTP status of OK if updated
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Rental> updateRental(@RequestBody Rental rental) {
        LOG.info("PUT /rentals " + rental);

        try {
            Rental m = rentalDao.updateRental(rental);
            if (m != null) {
                return new ResponseEntity<Rental>(rental, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a {@linkplain Rental rental} for the given id
     * 
     * @param id The id used to locate the {@link Rental rental}
     * 
     * @return ResponseEntity with {@link Rental rental} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/")
    public ResponseEntity<Rental> getRental(@RequestParam int id) {
        LOG.info("GET /rentals/?id=" + id);
        try {
            Rental rental = rentalDao.getRentalId(id);
            if (rental != null) {
                return new ResponseEntity<Rental>(rental, HttpStatus.OK);
            }
            else {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Rental>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Rental rental} whose name contains
     * the text in name
     * 
     * @param userId The userId parameter which contains the userId used to find the {@link Rental rentals}
     * @param onlyActive the onlyActive parameter if true will exclude all inactive rentals from the return 
     * 
     * @return ResponseEntity with array of {@link Rental rental} objects (may be empty) and
     * HTTP status of OK
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * GET http://localhost:8080/rentals/{userId}/{onlyActive}
     */
    @GetMapping("/{userId}/{onlyActive}")
    public ResponseEntity<Rental[]> getRentalsUser(@PathVariable int userId, @PathVariable boolean onlyActive) {
        LOG.info("GET /rentals/" + userId + "/" + onlyActive);
        try{
            Rental[] rentals = rentalDao.getRentalsUser(userId, onlyActive);
            return new ResponseEntity<Rental[]>(rentals,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Rental rental} with the given id
     * 
     * @param id The id of the {@link Rental rental} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Rental> deleteRental(@PathVariable int id) {
        LOG.info("DELETE /rentals/" + id);

        try{
            if( rentalDao.deleteRental(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
    * Responds to the GET request for all {@linkplain Rental rentals}
     * 
     * @return ResponseEntity with array of {@link Rental rental} objects (may be empty) and
     * HTTP status of OK
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{onlyActive}")
    public ResponseEntity<Rental[]> getAllRentals(@PathVariable boolean onlyActive){
        LOG.info("GET /rentals/" + onlyActive);
        try {
            return new ResponseEntity<>(rentalDao.getAllRentals(onlyActive), HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
