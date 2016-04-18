package com.amadeusounds.repository;

import com.amadeusounds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    public List<User> findByFirstName(String name);

    public User findByEmail(String email);
}
