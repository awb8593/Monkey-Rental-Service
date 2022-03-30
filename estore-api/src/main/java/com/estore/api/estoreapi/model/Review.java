package com.estore.api.estoreapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Review entity
 * 
 * @author Adrian Burgos awb8593
 */
public class Review {
    // implement Logger here when needed

    // package private for tests
    static final String STRING_FORMAT = "[id=%d, ratings=%s, reviews=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("ratings") private ArrayList<Integer> ratings;
    @JsonProperty("reviews") private ArrayList<String> reviews;

    /**
     * Create a review with the given id and lists of ratings and writings
     * @param id The id of the monkey that these reviews belong to
     * @param ratings the list of ratings (1-5) given to the specified monkey
     * @param reviews the list of reviews (written) given to the specified monkey
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Review(@JsonProperty("id") int id,
        @JsonProperty("ratings") ArrayList<Integer> ratings, 
        @JsonProperty("reviews") ArrayList<String> reviews) {
        this.id = id;
        this.ratings = ratings;
        this.reviews = reviews;
    }

    /**
     * Retrieves the id of the monkey that these reviews belong to
     * @return The id of the reviews' monkey
     */
    public int getId() {return id;}

    /**
     * Sets the review object's id to the id of a specified monkey - necessary for JSON object to Java object deserialization
     * @param id The id of the monkey for this review
     */
    public void setId(int id) {this.id = id;}

    /**
     * Sets the current list of ratings to the specified one
     * @param ratings the list of ratings being set
     */
    public void setRatings(ArrayList<Integer> ratings) {this.ratings = ratings;}

    /**
     * gets the list of ratings stored in this object
     * @return this object's ratings
     */
    public ArrayList<Integer> getRatings() {return this.ratings;}

    /**
     * Sets the current list of written reviews to the specified one
     * @param reviews the list of written reviews being set
     */
    public void setReviews(ArrayList<String> reviews) {this.reviews = reviews;}

    /**
     * gets the list of reviews stored in this object
     * @return this object's reviews
     */
    public ArrayList<String> getReviews() {return this.reviews;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,ratings,reviews);
    }

}