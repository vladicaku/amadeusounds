package com.amadeusounds.service.impl;

import com.amadeusounds.model.Rating;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import com.amadeusounds.repository.RatingRepository;
import com.amadeusounds.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Vac on 4/24/2016.
 */
@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public Rating findRatingById(Long id) {
        return ratingRepository.findOne(id);
    }

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.saveAndFlush(rating);
    }

    @Override
    public Rating updateRating(Rating rating) {
        return ratingRepository.saveAndFlush(rating);
    }

    @Override
    public void deleteRating(Rating rating) {
        ratingRepository.delete(rating);
    }

    @Override
    public void deleteRating(Long id) {
        ratingRepository.delete(id);
    }

    /**
     * TODO:
     * Da se razgovara za ova
     */
    @Override
    public double calculateRatingForSong(long id) {
        String queryString = "SELECT SUM(r.rating)*1.0/COUNT(r) FROM Rating r WHERE r.song.id = :id";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("id", id);
        Double result = (Double) query.getSingleResult();
        return result;
    }

    @Override
    public Rating findRatingByUserAndSong(User user, Song song) {
        return ratingRepository.findByUserAndSong(user, song);
    }
}
