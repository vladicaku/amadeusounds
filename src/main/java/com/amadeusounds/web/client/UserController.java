package com.amadeusounds.web.client;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.service.UserService;
import com.amadeusounds.view.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Slavce on 07.04.2016.
 */
@CrossOrigin()
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return new Response(ResponseType.OK, user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MappingJacksonValue getUserById(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        final MappingJacksonValue result = new MappingJacksonValue(user);
        result.setSerializationView(Views.UserSummaryView.class);
        return result;
    }

    @RequestMapping(path="/{id}/songs", method = RequestMethod.GET)
    public Response getUserSongs(@RequestParam("id") Long id) {
        List<Song> userSongs = userService.getUserSongs(id);
        return new Response(ResponseType.OK, userSongs);
    }

    @RequestMapping(path="/{id}/image", method = RequestMethod.GET)
    public void getUserImage(@RequestParam("id") Long id, HttpServletResponse response) throws IOException, SQLException {
        User user = userService.findUserById(id);
        Blob userImage = user.getImage();

        if (userImage != null) {
            OutputStream outputStream = response.getOutputStream();
            byte[] byteArray = userImage.getBytes(0, (int) userImage.length());
            outputStream.write(byteArray);
            outputStream.flush();
        } else {
            /**
             * TODO:
             * Return default image
             */
        }
    }

    @RequestMapping(value = "/search-by-name/{name}", method = RequestMethod.GET)
    public Response getUsersByName(@PathVariable("name") String name) {
        List<User> users = userService.findUserByName(name);
        return new Response(ResponseType.OK, users);
    }

    @RequestMapping(value = "/search-by-email/{email}", method = RequestMethod.GET)
    public Response getUserByEmail(@PathVariable("email") String email) {
        User user = userService.findUserByEmail(email);
        return new Response(ResponseType.OK, user);
    }

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }
}
