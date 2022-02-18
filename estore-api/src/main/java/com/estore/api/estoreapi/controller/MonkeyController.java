package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Monkey;
import com.estore.api.estoreapi.persistence.MonkeyDAO;

/**
 * Handles the REST API requests for the Monkey
 * 
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Trent Wesley taw8452
 * @author Jack Hester jrh3397
 * @author Eligh Ros edr5068
 */
@RestController
@RequestMapping("monkeys")
public class MonkeyController {
    private static final Logger LOG = Logger.getLogger(MonkeyController.class.getName());
    private MonkeyDAO monkeyDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param monkeyDao The {@link MonkeyDAO Monkey Data Access Object} to perform CRUD operations
     * 
     * This dependency is injected by the Spring Framework
     */
    public MonkeyController(MonkeyDAO monkeyDao) {
        this.monkeyDao = monkeyDao;
    }
    
    /**
     * Creates a {@linkplain Monkey monkey} with the provided monkey object
     * 
     * @param monkey - The {@link Monkey monkey} to create
     * 
     * @return ResponseEntity with created {@link Monkey monkey} object and HTTP status of CREATED
     * ResponseEntity with HTTP status of CONFLICT if {@link Monkey monkey} object already exists
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Monkey> createMonkey(@RequestBody Monkey monkey) {
        LOG.info("POST /monkeys " + monkey);

        if (monkey.getName() == "") {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        try {
            
            Monkey newMonkey = monkeyDao.createMonkey(monkey);
            return new ResponseEntity<Monkey>(newMonkey, HttpStatus.CREATED);

        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Monkey monkey} with the provided {@linkplain Monkey monkey} object, if it exists
     * 
     * @param monkey The {@link Monkey monkey} to update
     * 
     * @return ResponseEntity with updated {@link Monkey monkey} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Monkey> updateMonkey(@RequestBody Monkey monkey) {
        LOG.info("PUT /monkeys " + monkey);

        try {
            Monkey m = monkeyDao.updateMonkey(monkey);
            if (m != null) {
                return new ResponseEntity<Monkey>(monkey, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a {@linkplain Monkey monkey} for the given id
     * 
     * @param id The id used to locate the {@link Monkey monkey}
     * 
     * @return ResponseEntity with {@link Monkey monkey} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Monkey> getMonkey(@PathVariable int id) {
        LOG.info("GET /monkeys/" + id);
        try {
            Monkey monkey = monkeyDao.getMonkey(id);
            if (monkey != null) {
                return new ResponseEntity<Monkey>(monkey, HttpStatus.OK);
            }
            else {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {


            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Monkey>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Monkey monkey} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Monkey monkey}
     * 
     * @return ResponseEntity with array of {@link Monkey monkey} objects (may be empty) and
     * HTTP status of OK
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all monkeys that contain the text "ma"
     * GET http://localhost:8080/monkeys/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Monkey[]> searchMonkeys(@RequestParam String name) {
        LOG.info("GET /monkeys/?name="+name);
        try{
            Monkey[] monkeys = monkeyDao.findMonkeys(name);
            return new ResponseEntity<Monkey[]>(monkeys,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Monkey monkey} with the given id
     * 
     * @param id The id of the {@link Monkey monkey} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Monkey> deleteMonkey(@PathVariable int id) {
        LOG.info("DELETE /monkeys/" + id);

        try{
            if( monkeyDao.deleteMonkey(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
    * Responds to the GET request for all {@linkplain Monkey monkeys}
     * 
     * @return ResponseEntity with array of {@link Monkey monkey} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Monkey[]> getAllMonkeys(){
        LOG.info("GET /monkeys");
        try {
            return new ResponseEntity<>(monkeyDao.getAllMonkeys(), HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
