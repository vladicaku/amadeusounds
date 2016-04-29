package com.amadeusounds.service;

import com.amadeusounds.model.Comment;
import com.amadeusounds.model.Rating;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Slavce on 07.04.2016.
 */
public interface UserService {

    User registerUser(User user);

    List<Song> getUserSongs(long id);

    List<Comment> getUserComments(long id);

    List<Rating> getUserRatings(long id);

    List<User> findUserByName(String name);

    User findUserById(long id);

    User findUserByEmail(String email);

    User loginUser(String email, String password) throws Exception;

    User updateUser(User user);

    void deleteUser(User user);

    void deleteUser(Long id);

    User deactivateUser(User user);

    void saveImage(User user, MultipartFile multipartFile) throws IOException;

    void deleteImage(User user);

    void changePassword(User user, String oldPassword, String newPassword) throws Exception;
}