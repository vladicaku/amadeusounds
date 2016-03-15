package com.amadeusounds.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Blob;
import java.util.List;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
@Entity
@Table(name="users")
public class User extends BaseEntity {
    @Length(max=20)
    String username;

    @Email
    String email;

    @Length(max=50)
    String firstName;

    @Length(max=50)
    String secondName;

    @Length(max=50)
    String location;

    @OneToMany
    List<Song> songs;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
