package com.amadeusounds.service;

import com.amadeusounds.model.Comment;
import com.amadeusounds.model.Rating;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;

import java.util.List;

/**
 * Created by Slavce on 07.04.2016.
 */
public interface UserService {

    public User registerUser(User user);

    public List<Song> getUserSongs(long id);

    public List<Comment> getUserComments(long id);

    public List<Rating> getUserRatings(long id);

    public List<User> findUserByName(String name);

    public User findUserById(long id);

    public User findUserByEmail(String email);

    public User loginUser(String email, String password);
}