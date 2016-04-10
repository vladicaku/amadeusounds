package com.amadeusounds.service.impl;

import com.amadeusounds.model.Comment;
import com.amadeusounds.model.Rating;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import com.amadeusounds.repository.UserRepository;
import com.amadeusounds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Created by Slavce on 07.04.2016.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User registerUser(User user) {

        userRepository.saveAndFlush(user);

        return user;
    }

    @Override
    public List<Song> getUserSongs(long id) {

        User user = userRepository.findOne(id);

        return user.getSongs();
    }

    @Override
    public List<Comment> getUserComments(long id) {

        User user = userRepository.findOne(id);

        return user.getComments();
    }

    @Override
    public List<Rating> getUserRatings(long id) {

        User user = userRepository.findOne(id);

        return user.getRatings();
    }

    @Override
    public List<User> findUserByName(String name) {

        List<User> users = userRepository.findByfirstName(name);

        return users;
    }

    @Override
    public User findUserById(long id) {

        User user = userRepository.findOne(id);

        return user;
    }

    @Override
    public User findUserByEmail(String email) {

        User user = userRepository.findByemail(email);

        return user;
    }

    @Override
    public User loginUser(String email, String password) {

        User user = userRepository.findByemail(email);

        if(user.getPassword().equals(password)){
            return user;
        }

        return null;
    }
}
