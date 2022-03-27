package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Review;
import com.estore.api.estoreapi.model.Monkey;

/**
 * Implements the functionality for JSON file-based peristance for Reviews
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Adrian Burgos awb8593
 */
@Component
public class ReviewFileDAO implements ReviewDAO{

    private static final Logger LOG = Logger.getLogger(ReviewFileDAO.class.getName());
    Map<Integer,Review> reviews;   // Provides a local cache of the review objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Review
                                        // objects and JSON text format written
                                        // to the file
    private String filename;    // Filename to read from and write to
    
    /**
     * Creates a Review File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ReviewFileDAO(@Value("${reviews.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the reviews from the file
    }

    /**
     * Generates an array of {@linkplain Review reviews} from the tree map
     * 
     * @return  The array of {@link Review reviews}, may be empty
     */
    private Review[] getReviewsArray() {
        return getReviewsArray(null);
    }

    /**
     * Generates an array of {@linkplain Review reviews} from the tree map for any
     * {@linkplain Review review} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Review reviews}
     * in the tree map
     * 
     * @return  The array of {@link Review review}, may be empty
     */
    private Review[] getReviewsArray(int id) { // if containsText == null, no filter
        ArrayList<Review> reviewArrayList= new ArrayList<>();

        for (Review review : reviews.values()) {
            if (review.getId() == id) {
                reviewArrayList.add(review);
            }
        }

        Review[] reviewArray = new Review[reviewArrayList.size()];
        reviewArrayList.toArray(reviewArray);
        return reviewArray;
    }

    /**
     * Saves the {@linkplain Review reviews} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Review reviews} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Review[] reviewArray = getReviewsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),reviewArray);
        return true;
    }

    /**
     * Loads {@linkplain Review review} from the JSON file into the map
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        reviews = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of reviews
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Review[] reviewArray= objectMapper.readValue(new File(filename),Review[].class);

        // Add each review to the tree map and keep track of the greatest id
        for (Review review : reviewArray) {
            reviews.put(review.getId(), review);
        }
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Review createReview (Review review, Monkey monkey) throws IOException {
        synchronized(reviews) {
            // we use the passed in Monkey's ID to assign the ID of the review object
            // this way, reviews are easily grouped together and found for a specific monkey
            Review newReview = new Review(monkey.getId(), review.getRatings(), review.getReviews());
            reviews.put(newReview.getId(),newReview);
            save(); // may throw an IOException
            return newReview;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Review updateReview(Review review) throws IOException {
        synchronized(reviews) {
            if (reviews.containsKey(review.getId()) == false)
                return null;  // review does not exist

            reviews.put(review.getId(),review);
            save(); // may throw an IOException
            return review;
        }
    }

    /**
    ** {@inheritDoc}
     */
    public Review getReviews(int id) {
        synchronized(reviews) {
            if (reviews.containsKey(id)) {
                return reviews.get(id);
            }
            else {
                return null;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteReview(int id) throws IOException {
        synchronized(reviews) {
            if (reviews.containsKey(id)) {
                reviews.remove(id);
                return save();
            }
            else
                return false;
        }
    }


}
