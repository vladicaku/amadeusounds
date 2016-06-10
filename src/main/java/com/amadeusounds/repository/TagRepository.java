package com.amadeusounds.repository;

import com.amadeusounds.model.Category;
import com.amadeusounds.model.Tag;
import org.springframework.stereotype.Repository;

/**
 * Description
 *
 * @author Vladica Jovanovski
 */

@Repository
public interface TagRepository extends JpaSpecificationRepository<Tag, Long>{
    public Tag findByName(String name);
}
