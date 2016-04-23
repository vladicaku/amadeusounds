package com.amadeusounds.service.impl;

import com.amadeusounds.model.Rating;
import com.amadeusounds.repository.RatingRepository;
import com.amadeusounds.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Vac on 4/24/2016.
 */
@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepository;

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
}
