package com.amadeusounds.service.impl;

import com.amadeusounds.angularauth.AngularAuthenticationToken;
import com.amadeusounds.model.User;
import com.amadeusounds.service.AngularAuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Simple service for managing principals
 *
 * @author Vladica Jovanovski
 */
public class AngularAuthServiceImpl implements AngularAuthService{

    @Override
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        if (!(authentication instanceof AngularAuthenticationToken)){
            return null;
        }

        AngularAuthenticationToken angularAuthentication = (AngularAuthenticationToken) authentication;
        return  angularAuthentication.getUser();
    }
}
