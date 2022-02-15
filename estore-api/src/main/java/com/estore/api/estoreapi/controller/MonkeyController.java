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
        try {
            Monkey m = monkeyDao.createMonkey(monkey);
            if (monkey != null)
                return new ResponseEntity<Monkey>(m, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e){
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
