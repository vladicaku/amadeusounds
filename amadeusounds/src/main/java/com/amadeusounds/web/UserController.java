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
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {

        User user = userService.findUserById(id);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Content-Type", "application/json; charset=UTF-8");
        responseHeader.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<User>(user, responseHeader, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveUser/{name}/{secondName}/{email}/{password}/{image}/{location}/{biography}/{website}", method = RequestMethod.POST)
    public ResponseEntity<User> storeUser(@PathVariable("name") String name,
                          @PathVariable("secondName") String secondName,
                          @PathVariable("email") String email,
                          @PathVariable("password") String password,
                          @PathVariable("image") String image,
                          @PathVariable("location") String location,
                          @PathVariable("biography") String biography,
                          @PathVariable("website") String website) {

        User user = new User();

        user.setFirstName(name);
        user.setSecondName(secondName);
        user.setEmail(email);
        user.setPassword(password);

        //ke treba da se sredi!!
        //user.setImage(image);

        user.setLocation(location);
        user.setBiography(biography);
        user.setWebsite(website);

        User savedUser = userService.registerUser(user);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Content-Type", "application/json; charset=UTF-8");
        responseHeader.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<User>(savedUser, responseHeader, HttpStatus.OK);

        //return savedUser;
    }


    @RequestMapping(path="/users/{id}/songs", method = RequestMethod.GET)
    public List<Song> getUserSongs(@RequestParam("id") Long id) {

        List<Song> userSongs = userService.getUserSongs(id);

        return userSongs;
    }

    @RequestMapping(path="/users/{id}/comments", method = RequestMethod.GET)
    public List<Comment> getUserComments(@RequestParam("id") Long id) {

        List<Comment> userComments = userService.getUserComments(id);

        return userComments;
    }

    @RequestMapping(path="/users/{id}/ratings", method = RequestMethod.GET)
    public List<Rating> getUserRatings(@RequestParam("id") Long id) {

        List<Rating> userRatings = userService.getUserRatings(id);

        return userRatings;
    }

    @RequestMapping(value = "/getUsersByName/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersByName(@PathVariable("name") String name) {

        List<User> users = userService.findUserByName(name);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("Content-Type", "application/json; charset=UTF-8");
        responseHeader.add("Access-Control-Allow-Origin", "*");

        return new ResponseEntity<List<User>>(users, responseHeader, HttpStatus.OK);

        //return savedUser;
    }

    @RequestMapping(value = "/getUsersByEmail/{email}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {

        User user = userService.findUserByEmail(email);

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
