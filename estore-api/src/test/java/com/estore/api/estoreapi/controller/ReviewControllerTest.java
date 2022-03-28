package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.persistence.ReviewDAO;
import com.estore.api.estoreapi.model.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    @Test
    public void testGetReview() throws IOException {
        int id = 99;
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");
        
        Review review = new Review(id, ratings, reviews);

        when(mockReviewDAO.getReviews(review.getId())).thenReturn(review);

        ResponseEntity<Review> response = reviewController.getReview(review.getId());

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(review,response.getBody());
    }

    @Test
    public void testgetReviewNotFound() throws Exception {
        int id = 99;

        when(mockReviewDAO.getReviews(id)).thenReturn(null);

        ResponseEntity<Review> response = reviewController.getReview(id);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetReviewHandleException() throws Exception { 
        // Setup
        int id = 99;
        // When getReview is called on the Mock Review DAO, throw an IOException
        doThrow(new IOException()).when(mockReviewDAO).getReviews(id);

        // Invoke
        ResponseEntity<Review> response = reviewController.getReview(id);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateReview() throws IOException {  // createReview may throw IOException
        // Setup
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        Review review = new Review(99, ratings, reviews);

        // when createReview is called, return true simulating successful
        // creation and save
        when(mockReviewDAO.createReview(review, review.getId())).thenReturn(review);

        // Invoke
        ResponseEntity<Review> response = reviewController.createReview(review);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(review,response.getBody());
    }

    @Test
    public void testCreateReviewFailed() throws IOException {  // createReview may throw IOException
        // Setup
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        Review review = new Review(99, ratings, reviews);

        // when createReview is called, return false simulating failed
        // creation and save
        when(mockReviewDAO.createReview(review, review.getId())).thenReturn(null);

        // Invoke
        ResponseEntity<Review> response = reviewController.createReview(review);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateReviewHandleException() throws IOException {  // createReview may throw IOException
        // Setup
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        Review review = new Review(99, ratings, reviews);

        // When createReview is called on the Mock Review DAO, throw an IOException
        doThrow(new IOException()).when(mockReviewDAO).createReview(review, review.getId());

        // Invoke
        ResponseEntity<Review> response = reviewController.createReview(review);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    public void testUpdateReview() throws IOException { // updateReview may throw IOException
        // Setup
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        Review review = new Review(99, ratings, reviews);

        // when updateReview is called, return true simulating successful
        // creation and save
        when(mockReviewDAO.updateReview(review)).thenReturn(review);

        // Invoke
        ResponseEntity<Review> response = reviewController.createReview(review);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
    public void testUpdateReviewFailed() throws IOException {  // updateReview may throw IOException
        // Setup
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        Review review = new Review(99, ratings, reviews);

        // when updateReview is called, return false simulating failed
        // creation and save
        when(mockReviewDAO.createReview(review, review.getId())).thenReturn(null);

        // Invoke
        ResponseEntity<Review> response = reviewController.updateReview(review);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    public void testUpdateReviewHandleException() throws IOException {  // updateReview may throw IOException
        // Setup
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        Review review = new Review(99, ratings, reviews);

        // When updateReview is called on the Mock Review DAO, throw an IOException
        doThrow(new IOException()).when(mockReviewDAO).updateReview(review);

        // Invoke
        ResponseEntity<Review> response = reviewController.updateReview(review);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetAllReviews() throws IOException { // getAllReviews may throw IOException
        // Setup
        Review[] reviewList = new Review[2];

        ArrayList<Integer> ratings0 = new ArrayList<Integer>();
        ArrayList<String> reviews0 = new ArrayList<String>();
        ArrayList<Integer> ratings1 = new ArrayList<Integer>();
        ArrayList<String> reviews1 = new ArrayList<String>();
        
        ratings0.add(1);
        ratings1.add(3);
        reviews0.add("Seriously not a cool monkey");
        reviews1.add("Pooped on the floor");

        reviewList[0] = new Review(99, ratings0, reviews0);
        reviewList[1] = new Review (100, ratings1, reviews1);

        // when getAllReviews is called return the reviews created above
        when(mockReviewDAO.getAllReviews()).thenReturn(reviewList);

        // Invoke
        ResponseEntity<Review[]> response = reviewController.getAllReviews();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviewList, response.getBody());
    }

    @Test
    public void testGetAllReviewsHandleException() throws IOException { // getAllReviews may throw IOException
        // Setup
        // when getAllReviews is called on the Mock Review DAO, throw an IOException
        doThrow(new IOException()).when(mockReviewDAO).getAllReviews();

        // Invoke
        ResponseEntity<Review[]> response = reviewController.getAllReviews();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteReview() throws IOException {
        // Setup
        int reviewId = 99;
        // when deleteReview is called return true, simulating successful deletion
        when(mockReviewDAO.deleteReview(reviewId)).thenReturn(true);

        // Invoke
        ResponseEntity<Review> response = reviewController.deleteReview(reviewId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteReviewNotFound() throws IOException {
        // Setup
        int id = 99;
        // when deleteReview is called return false, simulating failed deletion
        when(mockReviewDAO.deleteReview(id)).thenReturn(false);

        // Invoke
        ResponseEntity<Review> response = reviewController.deleteReview(id);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteReviewHandleException() throws IOException {
        // Setup
        int id = 99;
        // When deleteReview is called on the Mock Review DAO, throw an IOException
        doThrow(new IOException()).when(mockReviewDAO).deleteReview(id);

        // Invoke
        ResponseEntity<Review> response = reviewController.deleteReview(id);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

}
