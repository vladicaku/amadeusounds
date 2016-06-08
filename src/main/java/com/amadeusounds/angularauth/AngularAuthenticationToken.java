package com.amadeusounds.angularauth;

import com.amadeusounds.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Vladica Jovanovski
 */
public class AngularAuthenticationToken implements Authentication {

  private Object principal;
  private Object credentials;
  private boolean authenticated = false;
  private User user;
  private String token;

  public AngularAuthenticationToken() {
    this.authenticated = false;
  }

  public AngularAuthenticationToken(Object principal, Object credentials) {
    this.principal = principal;
    this.credentials = credentials;
    this.authenticated = false;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  @Override
  public Object getDetails() {
    return user;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean b) throws IllegalArgumentException {
    this.authenticated = b;
  }

  @Override
  public String getName() {
    return user.getFirstName() + " " + user.getLastName();
  }
}
