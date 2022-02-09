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
    static final String STRING_FORMAT = "Monkey [id=%d, name=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;

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
    public Monkey(@JsonProperty("id") int id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name);
    }
}