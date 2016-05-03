package com.amadeusounds.security;

/**
 * Created by Slavce on 24.04.2016.
 */
public class TokenTransfer {
    private final String token;

    public TokenTransfer(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
