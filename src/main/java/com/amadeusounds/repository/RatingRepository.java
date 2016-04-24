package com.amadeusounds.repository;

import com.amadeusounds.model.Rating;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Vac on 4/24/2016.
 */
@Repository
public interface RatingRepository extends JpaSpecificationRepository<Rating, Long>{
    public Rating findByUserAndSong(User user, Song song);
}
