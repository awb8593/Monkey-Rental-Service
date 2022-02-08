package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Monkey;

/**
 * Implements the functionality for JSON file-based peristance for Monkeys
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Adrian Burgos awb8593
 */
public class MonkeyFileDAO implements MonkeyDAO{

    private static final Logger LOG = Logger.getLogger(MonkeyFileDAO.class.getName());
    Map<Integer,Monkey> monkeys;   // Provides a local cache of the monkey objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Monkeys
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new monkey
    private String filename;    // Filename to read from and write to
    /**
     * Generates the next id for a new {@linkplain Monkey monkey}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Monkey monkeys} from the tree map
     * 
     * @return  The array of {@link Monkey monkeys}, may be empty
     */
    private Monkey[] getMonkeysArray() {
        return getMonkeysArray();
    }

    /**
     * Saves the {@linkplain Monkey monkeys} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Monkey monkeys} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Monkey[] heroArray = getMonkeysArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),heroArray);
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    // @Override we will have to add back later, currently throwing an error because it is not overriding anything yet
    public Monkey createMonkey(Monkey monkey) throws IOException {
        synchronized(monkeys) {
            // We create a new hero object because the id field is immutable
            // and we need to assign the next unique id
            Monkey newMonkey = new Monkey(nextId(),monkey.getName());
            monkeys.put(newMonkey.getId(),newMonkey);
            save(); // may throw an IOException
            return newMonkey;
        }
    }
}
