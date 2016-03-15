package com.amadeusounds.repository;

import com.amadeusounds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
}
