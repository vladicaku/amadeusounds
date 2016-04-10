package com.amadeusounds.service.impl;

import com.amadeusounds.model.Rating;
import com.amadeusounds.repository.RatingRepository;
import com.amadeusounds.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Angela on 4/8/2016.
 */
@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @PersistenceContext
    private EntityManager em;


    @Override
    public Double findRating(Long id) {
        String qlString = "SELECT SUM(r.rating)*1.0/COUNT(r) FROM Rating r WHERE r.song.id="+id;
        Query q = em.createQuery(qlString);
        Double result = (Double)q.getSingleResult();
        return result;
    }

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.saveAndFlush(rating);
    }

    @Override
    public void deleteRating(Long ratingId) {
        ratingRepository.delete(ratingId);
    }

    @Override
    public Rating findRatingByUserAndSong(Long userId, Long songId) {
        return ratingRepository.findByUser_idAndSong_id(userId,songId);
    }


}
