package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import com.estore.api.estoreapi.persistence.ReviewDAO;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.estore.api.estoreapi.model.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.HeaderAssertions;

/**
 * Test the Review Controller class
 * 
 * @author Adrian Burgos awb8593
 */
@Tag("Controller-tier")
public class ReviewControllerTest {
    private ReviewController reviewController;
    private ReviewDAO mockReviewDAO;

    /**
     * Before each test, create a new ReviewController object and inject
     * a mock Review DAO
     */
    @BeforeEach
    public void setupReviewController() {
        mockReviewDAO = mock(ReviewDAO.class);
        reviewController = new ReviewController(mockReviewDAO);
    }

    
}
