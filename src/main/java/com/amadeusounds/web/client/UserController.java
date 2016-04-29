package com.amadeusounds.web.client;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import com.amadeusounds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Slavce on 07.04.2016.
 */

@RestController
@RequestMapping("/api/user")
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

    @RequestMapping(path="/{id}/songs", method = RequestMethod.GET)
    public List<Song> getUserSongs(@RequestParam("id") Long id) {

        List<Song> userSongs = userService.getUserSongs(id);

        return userSongs;
    }
}
