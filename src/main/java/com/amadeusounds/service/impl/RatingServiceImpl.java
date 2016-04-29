package com.amadeusounds.service.impl;

import com.amadeusounds.model.Rating;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import com.amadeusounds.repository.RatingRepository;
import com.amadeusounds.repository.SongRepository;
import com.amadeusounds.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;

/**
 * Created by Vac on 4/24/2016.
 */
@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public Rating findRatingById(Long id) {
        return ratingRepository.findOne(id);
    }

    @Override
    @Transactional
    public Rating saveRating(Rating rating) {
        return ratingRepository.saveAndFlush(rating);
    }

    @Override
    @Transactional
    public Rating updateRating(Rating rating) {
        return ratingRepository.saveAndFlush(rating);
    }

    @Override
    @Transactional
    public void deleteRating(Rating rating) {
        ratingRepository.delete(rating);
    }

    @Override
    @Transactional
    public void deleteRating(Long id) {
        ratingRepository.delete(id);
    }

    @Override
    @Transactional
    public Rating rate(Rating rating) {
        Rating oldRating = ratingRepository.findByUserAndSong(rating.getUser(), rating.getSong());
        Rating result;

        if (oldRating != null) {
            oldRating.setRating(rating.getRating());
            oldRating.setDate(LocalDate.now());
            result = oldRating;
        } else {
            rating.setDate(LocalDate.now());
            result = rating;
        }

        ratingRepository.saveAndFlush(result);
        // Recalculate song rating
        calculateRatingForSong(rating.getSong());
        return result;
    }

    @Transactional
    private void calculateRatingForSong(Song song) {
        String queryString = "SELECT SUM(r.rating) * 1.0 / COUNT(r) FROM Rating r WHERE r.song = :song";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("song", song);
        Double result = (Double) query.getSingleResult();
        song.setRating(result);
        songRepository.saveAndFlush(song);
    }
}
