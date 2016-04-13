package com.amadeusounds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Vac on 4/13/2016.
 */
@NoRepositoryBean
//@RepositoryDefinition
public interface JpaSpecificationRepository<T, S extends Serializable> extends JpaRepository<T, S>,
        JpaSpecificationExecutor<T> {
}



