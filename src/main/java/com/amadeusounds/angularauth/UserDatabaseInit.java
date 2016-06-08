package com.amadeusounds.angularauth;

import com.amadeusounds.model.User;
import com.amadeusounds.repository.UserRepository;
import com.amadeusounds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

/**
 * @author Vladica Jovanovski
 */
@org.springframework.context.annotation.Configuration
public class UserDatabaseInit {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.findByEmail("vladica@gmail.com") == null) {
            User user = new User();
            user.setActive(true);
            user.setFirstName("Vladica");
            user.setLastName("Jovanovski");
            user.setEmail("vladica@gmail.com");
            user.setPassword(passwordEncoder.encode("pass123"));
            user.setToken(passwordEncoder.encode(user.getFirstName()+user.getLastName()));
            userRepository.saveAndFlush(user);
            System.out.println("User Vladica created.");
        }
    }

}
