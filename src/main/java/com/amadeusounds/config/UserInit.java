package com.amadeusounds.config;

import com.amadeusounds.model.Role;
import com.amadeusounds.model.User;
import com.amadeusounds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by ristes on 3/9/16.
 */
@Component
public class UserInit {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  PasswordEncoder encoder;

  @PostConstruct
  public void init() {
    User admin = userRepository.findByEmail("admin@yahoo.com");
    if (admin == null) {
      admin = new User();
      admin.setEmail("admin@yahoo.com");
      admin.setPassword(encoder.encode("admin123"));
      admin.setRole(Role.ROLE_ADMIN);
      admin.setLastName("adminLName");
      admin.setFirstName("adminFName");
      userRepository.save(admin);
    }
  }
}
