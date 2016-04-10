package com.amadeusounds.repository;

import com.amadeusounds.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Angela on 4/7/2016.
 */

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long>{

    public Rating findByUser_idAndSong_id(Long user_id, Long song_id);

}
