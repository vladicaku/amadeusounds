package com.amadeusounds.web.admin;

import com.amadeusounds.model.User;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Vac on 4/29/2016.
 */
@CrossOrigin()
@RestController(value = "ApiUserController")
@RequestMapping("/api/admin/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response updateUser(@PathVariable(value = "id") Long id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
        return new Response(ResponseType.OK, "");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response deactivateUser(@PathVariable(value = "id") Long id) {
        User user = userService.findUserById(id);
        userService.deactivateUser(user);
        return new Response(ResponseType.OK, "");
    }

    @RequestMapping(value = "/{id}/password", method = RequestMethod.PUT)
    public Response changePassword(@PathVariable(value = "id") Long id,
                                   @RequestParam("oldPassword") String oldPassword,
                                   @RequestParam("newPassword") String newPassword) throws Exception {
        User user = userService.findUserById(id);
        userService.changePassword(user, oldPassword, newPassword);
        return new Response(ResponseType.OK, "");
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.POST)
    public Response uploadUserImage(@PathVariable(value = "id") Long id,
                                    @RequestParam("image") MultipartFile multipartFile) throws IOException {
        User user = userService.findUserById(id);
        userService.saveImage(user, multipartFile);
        return new Response(ResponseType.OK, "");
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.POST)
    public Response deleteUserImage(@PathVariable(value = "id") Long id) {
        User user = userService.findUserById(id);
        userService.deleteImage(user);
        return new Response(ResponseType.OK, "");
    }

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }
}
