package com.amadeusounds.web.client;

import com.amadeusounds.model.User;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.service.LoginService;
import com.amadeusounds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * LoginController
 *
 * @author Vladica Jovanovski
 */
@CrossOrigin()
@RestController
@RequestMapping("/api")
public class LoginController {

//    @Autowired
//    LoginService loginService;
//
//    @Autowired
//    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestParam("email") String email,
                          @RequestParam("email") String password) {
        return new Response(ResponseType.OK, "");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Response logout() {
        return new Response(ResponseType.OK, "");
    }
}
