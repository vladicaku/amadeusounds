package com.amadeusounds.security;

/**
 * Created by Slavce on 24.04.2016.
 */
public class UserTransfer {

    private final String username;
    private final String role;

    public UserTransfer(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

}
