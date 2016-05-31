package com.amadeusounds.repository;

import com.amadeusounds.model.Category;
import org.springframework.stereotype.Repository;

/**
 * Description
 *
 * @author Vladica Jovanovski
 */
@Repository
public interface CategoryRepository extends JpaSpecificationRepository<Category, Long> {
}
