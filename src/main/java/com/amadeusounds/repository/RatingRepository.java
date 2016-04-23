package com.amadeusounds.repository;

import com.amadeusounds.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Vac on 4/24/2016.
 */
@Repository
public interface RatingRepository extends JpaSpecificationRepository<Rating, Long>{
}
