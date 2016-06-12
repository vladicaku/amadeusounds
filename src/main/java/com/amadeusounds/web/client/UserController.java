package com.amadeusounds.web.client;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.service.AngularAuthService;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Slavce on 07.04.2016.
 */
@CrossOrigin()
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AngularAuthService angularAuthService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return new Response(ResponseType.OK, user);
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public MappingJacksonValue getUserById() {
        User user1 = angularAuthService.getUser();

        User user = userService.findUserById(user1.getId());

        final MappingJacksonValue result = new MappingJacksonValue(user);
        result.setSerializationView(Views.UserSummaryView.class);
        return result;
    }

    @RequestMapping(path="/{id}/songs", method = RequestMethod.GET)
    public Response getUserSongs(@RequestParam("id") Long id) {
        List<Song> userSongs = userService.getUserSongs(id);
        return new Response(ResponseType.OK, userSongs);
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.POST)
    public Response uploadUserImage(@PathVariable(value = "id") Long id,
                                    @RequestParam("image") MultipartFile multipartFile) throws IOException {
        User user = userService.findUserById(id);
        userService.saveImage(user, multipartFile);
        return new Response(ResponseType.OK, "");
    }

    @RequestMapping(path="/{id}/image", method = RequestMethod.GET)
    public void getUserImage(@PathVariable("id") Long id, HttpServletResponse response) throws IOException, SQLException {
        User user = userService.findUserById(id);
        Blob userImage = user.getImage();

        if (userImage != null) {
//            OutputStream outputStream = response.getOutputStream();
//            byte[] byteArray = userImage.getBytes(0, (int) userImage.length());
//            outputStream.write(byteArray);
//            outputStream.flush();

            try {
                OutputStream outputStream = response.getOutputStream();
                byte[] byteArray = userImage.getBytes(1, (int) userImage.length());
                response.setContentType("image/png");
                response.setContentLengthLong(byteArray.length);
                outputStream.write(byteArray);
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            User defoltUserImage = userService.findUserByEmail("defolt@gmail.com");

            Blob defoltUserImageB = defoltUserImage.getImage();

            if(defoltUserImageB != null) {
//                OutputStream outputStream = response.getOutputStream();
//                byte[] byteArray = defoltUserImageB.getBytes(0, (int) defoltUserImageB.length());
//                outputStream.write(byteArray);
//                outputStream.flush();

                try {
                    OutputStream outputStream = response.getOutputStream();
                    byte[] byteArray = defoltUserImageB.getBytes(1, (int) defoltUserImageB.length());
                    response.setContentType("image/png");
                    response.setContentLengthLong(byteArray.length);
                    outputStream.write(byteArray);
                    outputStream.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
