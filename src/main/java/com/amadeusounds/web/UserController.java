package com.amadeusounds.web;

import com.amadeusounds.model.Comment;
import com.amadeusounds.model.Rating;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import com.amadeusounds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Blob;
import java.util.List;

/**
 * Created by Slavce on 07.04.2016.
 */

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {

        User user = userService.findUserById(id);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Content-Type", "application/json; charset=UTF-8");
        responseHeader.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<User>(user, responseHeader, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> storeUser(@RequestBody User user) {

        User savedUser = userService.registerUser(user);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Content-Type", "application/json; charset=UTF-8");
        responseHeader.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<User>(savedUser, responseHeader, HttpStatus.OK);

    }


    @RequestMapping(path="/users/{id}/songs", method = RequestMethod.GET)
    public List<Song> getUserSongs(@PathVariable Long id) {

        List<Song> userSongs = userService.getUserSongs(id);

        return userSongs;
    }

    @RequestMapping(path="/users/{id}/comments", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getUserComments(@PathVariable Long id) {

        List<Comment> userComments = userService.getUserComments(id);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Content-Type", "application/json; charset=UTF-8");
        responseHeader.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<List<Comment>>(userComments, responseHeader, HttpStatus.OK);
    }

    @RequestMapping(path="/users/{id}/ratings", method = RequestMethod.GET)
    public ResponseEntity<List<Rating>> getUserRatings(@PathVariable Long id) {

        List<Rating> userRatings = userService.getUserRatings(id);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Content-Type", "application/json; charset=UTF-8");
        responseHeader.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<List<Rating>>(userRatings, responseHeader, HttpStatus.OK);

    }

    @RequestMapping(value = "/getUsersByName/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersByName(@PathVariable String name) {

        List<User> users = userService.findUserByName(name);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Content-Type", "application/json; charset=UTF-8");
        responseHeader.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<List<User>>(users, responseHeader, HttpStatus.OK);

    }

    @RequestMapping(value = "/getUsersByEmail/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {

        User user = userService.findUserByEmail(email);

        System.out.print(user);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Content-Type", "application/json; charset=UTF-8");
        responseHeader.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<User>(user, responseHeader, HttpStatus.OK);

        //return savedUser;
    }

    @RequestMapping(value = "/login/{email}/{password}", method = RequestMethod.GET)
    public ResponseEntity<User> loginUser(@PathVariable("email") String email,
                                          @PathVariable("password") String password) {

        User user = userService.loginUser(email, password);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Content-Type", "application/json; charset=UTF-8");
        responseHeader.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<User>(user, responseHeader, HttpStatus.OK);

        //return savedUser;
    }
}