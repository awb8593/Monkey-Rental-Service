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

import com.estore.api.estoreapi.model.Monkey;

/**
 * Implements the functionality for JSON file-based peristance for Monkeys
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Adrian Burgos awb8593
 * @author Trent Wesley taw8452
 */
@Component
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
     * Creates a Monkey File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public MonkeyFileDAO(@Value("${monkeys.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the monkeys from the file
    }

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
        return getMonkeysArray(null);
    }

    /**
     * Generates an array of {@linkplain Monkey monkey} from the tree map for any
     * {@linkplain Monkey monkey} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Monkey monkey}
     * in the tree map
     * 
     * @return  The array of {@link Monkey monkey}, may be empty
     */
    private Monkey[] getMonkeysArray(String containsText) { // if containsText == null, no filter
        ArrayList<Monkey> monkeyArrayList = new ArrayList<>();

        for (Monkey monkey : monkeys.values()) {
            if (containsText == null || monkey.getName().contains(containsText)) {
                monkeyArrayList.add(monkey);
            }
        }

        Monkey[] monkeyArray = new Monkey[monkeyArrayList.size()];
        monkeyArrayList.toArray(monkeyArray);
        return monkeyArray;
    }

    /**
     * Saves the {@linkplain Monkey monkeys} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Monkey monkeys} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Monkey[] monkeyArray = getMonkeysArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),monkeyArray);
        return true;
    }

    /**
     * Loads {@linkplain Monkey monkey} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        monkeys = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of monkeys
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Monkey[] monkeyArray = objectMapper.readValue(new File(filename),Monkey[].class);

        // Add each monkey to the tree map and keep track of the greatest id
        for (Monkey monkey : monkeyArray) {
            monkeys.put(monkey.getId(), monkey);
            if (monkey.getId() > nextId)
                nextId = monkey.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Monkey createMonkey(Monkey monkey) throws IOException {
        synchronized(monkeys) {
            // We create a new monkey object because the id field is immutable
            // and we need to assign the next unique id
            Monkey newMonkey = new Monkey(nextId(),monkey.getName());
            monkeys.put(newMonkey.getId(),newMonkey);
            save(); // may throw an IOException
            return newMonkey;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
Update-a-product
    public Monkey updateMonkey(Monkey monkey) throws IOException {
        synchronized(monkeys) {
            if (monkeys.containsKey(monkey.getId()) == false)
                return null;  // monkey does not exist

            monkeys.put(monkey.getId(),monkey);
            save(); // may throw an IOException
            return monkey;
        }
    }

    public Monkey getMonkey(int id) {
            synchronized(monkeys) {
                if (monkeys.containsKey(id)) {
                    return monkeys.get(id);
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
    public Monkey[] findMonkeys(String containsText) {
        synchronized(monkeys) {
            return getMonkeysArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteMonkey(int id) throws IOException {
        synchronized(monkeys) {
            if (monkeys.containsKey(id)) {
                monkeys.remove(id);
                return save();
            }
            else
                return false;
        }
    }

main
}
