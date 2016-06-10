package com.amadeusounds.service.impl;

import com.amadeusounds.model.Comment;
import com.amadeusounds.model.Rating;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import com.amadeusounds.repository.UserRepository;
import com.amadeusounds.service.UserService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slavce on 07.04.2016.
 */

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findUserById(long id) {
        User user = userRepository.findOne(id);
        return user;
    }

    @Override
    public List<User> findUserByName(String name) {
        List<User> users = userRepository.findByFirstName(name);
        return users;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSongs(new ArrayList<>());
        user.setComments(new ArrayList<>());
        user.setRatings(new ArrayList<>());
        userRepository.saveAndFlush(user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User oldUser = findUserById(user.getId());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());
        userRepository.saveAndFlush(oldUser);
        return oldUser;
    }

    @Override
    @Transactional
    public void changePassword(User user, String oldPassword, String newPassword) throws Exception {
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            if (oldPassword.equals(newPassword)) {
                throw new Exception("The old and the new password cannot be same.");
            }
            else {
                user.setPassword(passwordEncoder.encode(newPassword));
            }
        }
        else {
            throw new Exception("Wrong password.");
        }
    }

    @Override
    @Transactional
    public void saveImage(User user, MultipartFile multipartFile) throws IOException {
        Blob blob = Hibernate.getLobCreator(getCurrentSession()).createBlob(multipartFile.getInputStream(), multipartFile.getSize());
        user.setImage(blob);
        userRepository.saveAndFlush(user);
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
    @Transactional
    public User deactivateUser(User user) {
        user.setActive(false);
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void deleteImage(User user) {
        user.setImage(null);
        userRepository.saveAndFlush(user);
    }

    protected Session getCurrentSession()  {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        System.err.println(user);

        if (user != null) {
            SimpleGrantedAuthority role = new SimpleGrantedAuthority(user.role.toString());
            List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
            roles.add(role);

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
        } else {
            return null;
        }
    }

    @Override
    public User findByToken(String token) {
        return  userRepository.findByToken(token);
    }
}