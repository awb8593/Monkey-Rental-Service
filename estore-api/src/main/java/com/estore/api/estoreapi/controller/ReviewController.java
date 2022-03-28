package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Review;
import com.estore.api.estoreapi.persistence.ReviewDAO;

/**
 * Handles the REST API requests for Reviews
 * 
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Adrian Burgos awb8593
 */
public class ReviewController {
    private static final Logger LOG = Logger.getLogger(ReviewController.class.getName());
    private ReviewDAO reviewDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param reviewDAO The {@link ReviewDAO Review Data Access Object} to perform CRUD operations
     * 
     * This dependency is injected by the Spring Framework
     */
    public ReviewController(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    /**
     * Creates a {@linkplain Review review} with the provided review object
     * 
     * @param review - The {@link Review review} to create
     * 
     * @return ResponseEntity with created {@link Review review} object and HTTP status of CREATED
     * ResponseEntity with HTTP status of CONFLICT if {@link Review review} is set to have an empty name
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        LOG.info("POST /reviews " + review);

        if (review.getId() == -1) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        try {
            
            Review newReview = reviewDAO.createReview(review, review.getId());
            return new ResponseEntity<Review>(newReview, HttpStatus.CREATED);

        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Review review} with the provided {@linkplain Review review} object, if it exists
     * 
     * @param review The {@link Review review} to update
     * 
     * @return ResponseEntity with updated {@link Review review} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Review> updateReview(@RequestBody Review review) {
        LOG.info("PUT /reviews " + review);

        try {
            Review r = reviewDAO.updateReview(review);
            if (r != null) {
                return new ResponseEntity<Review>(review, HttpStatus.OK);
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
     * Responds to the GET request for a {@linkplain Review review} for the given id
     * 
     * @param id The id used to locate the {@link Review review}
     * 
     * @return ResponseEntity with {@link Review review} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable int id) {
        LOG.info("GET /reviews/" + id);
        try {
            Review review = reviewDAO.getReviews(id);
            if (review != null) {
                return new ResponseEntity<Review>(review, HttpStatus.OK);
            }
            else {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {


            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Review>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Review review} with the given id
     * 
     * @param id The id of the {@link Review review} to be deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable int id) {
        LOG.info("DELETE /reviews/" + id);

        try{
            if( reviewDAO.deleteReview(id)) {
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
    * Responds to the GET request for all {@linkplain Review review}
     * 
     * @return ResponseEntity with array of {@link Review review} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Review[]> getAllReviews(){
        LOG.info("GET /reviews");
        try {
            return new ResponseEntity<>(reviewDAO.getAllReviews(), HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
