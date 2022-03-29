package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Review class
 * 
 * @author Adrian Burgos awb8593
 */
@Tag("Model-tier")
public class ReviewTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        // Invoke
        Review review = new Review(expected_id, ratings, reviews);

        // Analyze
        assertEquals(expected_id, review.getId());
        assertEquals(ratings, review.getRatings());
        assertEquals(reviews, review.getReviews());        
    }

    @Test
    public void testID() {
        int expected_id = 99;
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        int testID = 100;
        Review review = new Review(testID, ratings, reviews);

        review.setId(expected_id);

        assertEquals(expected_id, review.getId());
    }

    @Test
    public void testRatings() {
        int expected_id = 99;
        ArrayList<Integer> expectedRatings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        expectedRatings.add(1);
        expectedRatings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        ArrayList<Integer> testRatings = new ArrayList<Integer>();
        testRatings.add(5);

        Review review = new Review(expected_id, testRatings, reviews);

        review.setRatings(expectedRatings);

        assertEquals(expectedRatings, review.getRatings());
    }

    @Test
    public void testReviews() {
        int expected_id = 99;
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> expectedReviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        expectedReviews.add("Seriously not a cool monkey");
        expectedReviews.add("Pooped on the floor");

        ArrayList<String> testReviews = new ArrayList<String>();
        testReviews.add("A great, terrific monkey");

        Review review = new Review(expected_id, ratings, testReviews);

        review.setReviews(expectedReviews);

        assertEquals(expectedReviews, review.getReviews());
    }

    @Test
    public void testToString() {
        int id = 99;
        ArrayList<Integer> ratings = new ArrayList<Integer>();
        ArrayList<String> reviews = new ArrayList<String>();
        
        ratings.add(1);
        ratings.add(3);
        reviews.add("Seriously not a cool monkey");
        reviews.add("Pooped on the floor");

        Review review = new Review(id, ratings, reviews);

        String expected_string = String.format(Review.STRING_FORMAT,id,ratings,reviews,false);

        String actual_string = review.toString();

        assertEquals(expected_string, actual_string);
    
    }


}
