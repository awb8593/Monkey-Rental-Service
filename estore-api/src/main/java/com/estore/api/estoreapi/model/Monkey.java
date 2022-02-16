package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Monkey entity
 * 
 * @author Jack Yakubison jcy4561
 */
public class Monkey {
    private static final Logger LOG = Logger.getLogger(Monkey.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Monkey [id=%d, name=%s, price=%f, species=%s, description=%s, rented=%b]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") float price;
    @JsonProperty("species") String species;
    @JsonProperty("description") String desc;
    @JsonProperty("rented") boolean rented;

    /**
     * Create a monkey with the given id and name
     * @param id The id of the monkey
     * @param name The name of the monkey
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Monkey(@JsonProperty("id") int id,
        @JsonProperty("name") String name, 
        @JsonProperty("price") float price, 
        @JsonProperty("species") String species, 
        @JsonProperty("description") String desc) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.species = species;

        this.rented = false;
    }

    /**
     * Retrieves the id of the monkey
     * @return The id of the monkey
     */
    public int getId() {return id;}

    /**
     * Sets the name of the monkey - necessary for JSON object to Java object deserialization
     * @param name The name of the monkey
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the monkey
     * @return The name of the monkey
     */
    public String getName() {return name;}
    

    /**
     * Sets the price of the monkey - necessary for JSON object to Java object deserialization
     * @param price The price of the monkey
     */
    public void setPrice(float price) {this.price = price;}

    /**
     * Retrieves the price of the monkey
     * @return The price of the monkey
     */
    public float getPrice() {return price;}


    /**
     * Sets the species of the monkey - necessary for JSON object to Java object deserialization
     * @param species The species of the monkey
     */
    public void setSpecies(String species) {this.species = species;}

    /**
     * Retrieves the species of the monkey
     * @return The species of the monkey
     */
    public String getSpecies() {return species;}


    /**
     * Sets the description of the monkey - necessary for JSON object to Java object deserialization
     * @param desc The description of the monkey
     */
    public void setDescription(String desc) {this.desc = desc;}

    /**
     * Retrieves the description of the monkey
     * @return The description of the monkey
     */
    public String getDescription() {return desc;}

    /**
     * Sets the rented status of the monkey - necessary for JSON object to Java object deserialization
     * @param rented The rented status of the monkey
     */
    public void setRented(boolean rented) {this.rented = rented;}

    /**
     * Retrieves the rented status of the monkey
     * @return The rented status of the monkey
     */
    public boolean getRented() {return rented;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,price,species,desc,rented);
    }
}