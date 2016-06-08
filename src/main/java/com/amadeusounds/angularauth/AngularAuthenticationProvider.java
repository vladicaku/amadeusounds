package com.amadeusounds.angularauth;

import com.amadeusounds.model.User;
import com.amadeusounds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Vladica Jovanovski
 */
@Component
public class AngularAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof AngularAuthenticationToken) {
            AngularAuthenticationToken angularAuthentication = (AngularAuthenticationToken) authentication;

            /**
             * Check the validity of the token if exist
             */
            if (angularAuthentication.getToken() != null) {
                User user = userService.findByToken(angularAuthentication.getToken());
                if (user != null) {
                    angularAuthentication.setUser(user);
                    angularAuthentication.setAuthenticated(true);
                }
            }
            /**
             * Check against username and password
             */
            else {
                User user = userService.findUserByEmail(angularAuthentication.getPrincipal().toString());
                if (user != null && passwordEncoder.matches((String) angularAuthentication.getCredentials(), user.getPassword())) {
                    angularAuthentication.setUser(user);
                    angularAuthentication.setToken(user.getToken());
                    angularAuthentication.setAuthenticated(true);
                }
            }
            return angularAuthentication;
        }
        else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return AngularAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
