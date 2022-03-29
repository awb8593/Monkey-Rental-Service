package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Review File DAO class
 * 
 * @author Adrian Burgos awb8593
 */
@Tag("Persistence-tier")
public class ReviewFileDAOTest {
    ReviewFileDAO reviewFileDAO;
    Review[] testReviews;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupReviewFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testReviews = new Review[3];

        ArrayList<Integer> ratings1 = new ArrayList<Integer>();
        ArrayList<String> reviews1 = new ArrayList<String>();
        ratings1.add(1);
        reviews1.add("Seriously not a cool monkey");

        ArrayList<Integer> ratings2 = new ArrayList<Integer>();
        ArrayList<String> reviews2 = new ArrayList<String>();
        ratings2.add(5);
        reviews2.add("a highly intelligent and polite monkey");

        ArrayList<Integer> ratings3 = new ArrayList<Integer>();
        ArrayList<String> reviews3 = new ArrayList<String>();
        ratings3.add(3);
        reviews3.add("an average monkey, no complaints");

        testReviews[0] = new Review(99, ratings1, reviews1);
        testReviews[1] = new Review(100, ratings2, reviews2);
        testReviews[2] = new Review(101, ratings3, reviews3);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the review array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matterReviews.txt"),Review[].class))
                .thenReturn(testReviews);
        reviewFileDAO = new ReviewFileDAO("doesnt_matterReviews.txt",mockObjectMapper);
    }

    @Test
    public void testDeleteReview() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> reviewFileDAO.deleteReview(99), "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test reviews array - 1 (because of the delete)
        // Because reviews attribute of ReviewFileDAO is package private
        // we can access it directly
        assertEquals(reviewFileDAO.reviews.size(),testReviews.length-1);
    }

    @Test
    public void testCreateReview() {
        // Setup
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        Review review = new Review(99, ratings, reviews);

        // Invoke
        Review result = assertDoesNotThrow(() -> reviewFileDAO.createReview(review, review.getId()),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Review actual = reviewFileDAO.getReviews(review.getId());
        assertEquals(actual.getId(),review.getId());
        assertEquals(actual.getRatings(), review.getRatings());
        assertEquals(actual.getReviews(), review.getReviews());
    }

    @Test
    public void testUpdateReview() {
        // Setup
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        Review review = new Review(99, ratings, reviews);

        // Invoke
        Review result = assertDoesNotThrow(() -> reviewFileDAO.updateReview(review),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Review actual = reviewFileDAO.getReviews(review.getId());
        assertEquals(actual,review);
    }

    @Test
    public void testGetAllReviews() throws IOException {
        // Invoke
        Review[] reviews = reviewFileDAO.getAllReviews();

        // Analyze
        assertEquals(reviews.length,testReviews.length);
        for (int i = 0; i < testReviews.length;++i)
            assertEquals(reviews[i],testReviews[i]);
    }

    @Test
    public void testGetReview() {
        // Invoke
        Review review = reviewFileDAO.getReviews(99);

        // Analyze
        assertEquals(review, testReviews[0]);
    }

    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Review[].class));

        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        Review review = new Review(99, ratings, reviews);

        assertThrows(IOException.class,
                        () -> reviewFileDAO.createReview(review, review.getId()),
                        "IOException not thrown");
    }

    @Test
    public void testGetReviewNotFound() {
        // Invoke
        Review review = reviewFileDAO.getReviews(98);

        // Analyze
        assertEquals(review,null);
    }

    @Test
    public void testDeleteReviewNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> reviewFileDAO.deleteReview(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(reviewFileDAO.reviews.size(),testReviews.length);
    }

    @Test
    public void testUpdateReviewNotFound() {
        // Setup
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(4);
        ratings.add(5);
        reviews.add("Amazing");
        reviews.add("Incredible");

        Review review = new Review(98, ratings, reviews);

        // Invoke
        Review result = assertDoesNotThrow(() -> reviewFileDAO.updateReview(review),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the ReviewFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matterReviews.txt"),Review[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new ReviewFileDAO("doesnt_matterReviews.txt",mockObjectMapper),
                        "IOException not thrown");
    }

}
