package com.amadeusounds.repository;

import com.amadeusounds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Vac on 3/15/2016.
 */
@Repository
public interface UserRepository extends JpaSpecificationRepository<User, Long>{

    public List<User> findByFirstName(String name);

    public User findByEmail(String email);

    public User findByToken(String token);

}
