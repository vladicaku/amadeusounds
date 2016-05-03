package com.amadeusounds.web.client;

import com.amadeusounds.model.User;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.security.TokenTransfer;
import com.amadeusounds.security.TokenUtils;
import com.amadeusounds.security.UserTransfer;
import com.amadeusounds.service.LoginService;
import com.amadeusounds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    UserService userService;

    private static final int TOKEN_DURATION = 30 * 24 * 60 * 60; // 30 days

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authManager;

        //ne trebaat!!!
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public Response login(@RequestParam("email") String email,
//                          @RequestParam("email") String password) {
//        return new Response(ResponseType.OK, "");
//    }
//
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public Response logout() {
//        return new Response(ResponseType.OK, "");
//    }

    /**
     * Authenticates a user and creates an authentication token.
     *
     * @param username The name of the user.
     * @param password The password of the user.
     * @return A transfer containing the authentication token.
     */
    @RequestMapping(value = "/user/authenticate", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public TokenTransfer authenticate(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("rememberMe") boolean rememberMe,
            HttpServletRequest request, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password);
        Authentication authentication = this.authManager
                .authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

		/*
            * Reload user as password of authentication principal will be null
		    * after authorization and password is needed for token generation
		 */
        UserDetails userDetails = this.userService.loadUserByUsername(username);
        Cookie cookie = new Cookie("token", TokenUtils.createToken(userDetails));
        if (rememberMe) {
            cookie.setMaxAge(TOKEN_DURATION);
        }
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);

        TokenTransfer tokenTransfer = new TokenTransfer(TokenUtils.createToken(userDetails));

        return tokenTransfer;

        //return new TokenTransfer(TokenUtils.createToken(userDetails));
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public void authenticate(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("temp_token")) {
                c.setMaxAge(TOKEN_DURATION);
                c.setPath(request.getContextPath());
                response.addCookie(c);
                System.out.println("Found cookie");
                return;
            }
        }
        Cookie cookie = new Cookie("temp_token", UUID.randomUUID().toString());
        cookie.setMaxAge(TOKEN_DURATION);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public UserTransfer getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof String
                && ((String) principal).equals("anonymousUser")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (principal instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) principal;
            User user = userService.findUserByEmail(userDetails.getUsername());
            return new UserTransfer(user.getEmail(), user.getRole().toString());
        }
        return null;
    }
}
