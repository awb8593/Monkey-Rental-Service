package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Review entity
 * 
 * @author Adrian Burgos awb8593
 */
public class Review {
    // implement Logger here when needed

    static final String STRING_FORMAT = "Reviews For Monkey [id=%d, ratings=%d, reviews=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("ratings") private ArrayList<Integer> ratings;
    @JsonProperty("reviews") private ArrayList<String> reviews;
}