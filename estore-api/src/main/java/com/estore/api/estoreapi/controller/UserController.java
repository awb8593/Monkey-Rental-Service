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

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.Monkey;

/**
 * Handles the REST API requests for the User
 * 
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Jack Yakubison jcy4561
 */
@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDAO userDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param userDao The {@link UserDao User Data Access Object} to perform CRUD operations
     * 
     * This dependency is injected by the Spring Framework
     */
    public UserController(UserDAO userDao) {
        this.userDao = userDao;
    }
    
    /**
     * Creates a {@linkplain User User} with the provided user object
     * 
     * @param user - The {@link User user} to create
     * 
     * @return ResponseEntity with created {@link User user} object and HTTP status of CREATED
     * ResponseEntity with HTTP status of CONFLICT if {@link User user}'s username already exists
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /users " + user);

        try {
            User[] userList = userDao.getAllUsers();
            if(userList != null)
            {
                for(int i = 0; i < userList.length; i++){
                    if(userList[i].getUsername().equals(user.getUsername())) {
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                    }
                }
            }
            User newUser = userDao.createUser(user);
            return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the PUT request to update a {@linkplain User user} with a given id
     * 
     * Updates the {@linkplain User user} with the provided {@linkplain User user} object, if it exists
     * 
     * @param user The {@link User user} to update
     * 
     * @return ResponseEntity with updated {@link User user} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        LOG.info("PUT /users " + user);

        try {
            User m = userDao.updateUser(user);
            if (m != null) {
                if(!m.equals(user)) {
                    return new ResponseEntity<User>(m, HttpStatus.NOT_ACCEPTABLE);
                }   
                else {
                    return new ResponseEntity<User>(user, HttpStatus.OK);
                }
                
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
     * Responds to the GET request for a {@linkplain User user} for the given id
     * 
     * @param id The id used to locate the {@link User user}
     * 
     * @return ResponseEntity with {@link User user} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        LOG.info("GET /users/" + id);
        try {
            User user = userDao.getUserId(id);
            if (user != null) {
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
            else {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {


            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for the {@linkplain User user} whose username matches the input userame
     * 
     * @param username The username parameter which contains the text used to find the {@link User user}
     * 
     * @return ResponseEntity a {@link User user} and HTTP status of OK, or NOT_FOUND
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find the user with the username bob125
     * GET http://localhost:8080/users/?username=bob125
     */
    @GetMapping("/")
    public ResponseEntity<User> getUserByName(@RequestParam String username) {
        LOG.info("GET /users/?username="+username);
        try{
            User user = userDao.getUserName(username);
            if( user != null) {
                return new ResponseEntity<User>(user,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } 
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
    * Responds to the GET request for all {@linkplain User users}
     * 
     * @return ResponseEntity with array of {@link User user} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<User[]> getAllUsers(){
        LOG.info("GET /users");
        try {
            return new ResponseEntity<>(userDao.getAllUsers(), HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to GET request for all monkeys in a user cart
     * 
     * @param id ID of a User
     * @return ResponseEntity with array of {@link Monkey monkey} objects and 
     * HTTP status of OK
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/cart/{id}")
    public ResponseEntity<Monkey[]> getUserCart(@PathVariable int id){
        LOG.info("GET /users/cart/" + id);
        try {
            return new ResponseEntity<Monkey[]>(userDao.getUserCart(id), HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

