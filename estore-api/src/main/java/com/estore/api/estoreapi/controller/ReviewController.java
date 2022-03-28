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

}
