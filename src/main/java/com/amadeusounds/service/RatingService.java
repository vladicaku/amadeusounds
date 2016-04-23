package com.amadeusounds.service;

import com.amadeusounds.model.Rating;

/**
 * Created by Vac on 4/24/2016.
 */
public interface RatingService {

    public Rating findRatingById(Long id);

    Rating saveRating(Rating rating);

    Rating updateRating(Rating rating);

    void deleteRating(Rating rating);

    void deleteRating(Long id);

}
