package com.amadeusounds.service;

import com.amadeusounds.model.Rating;

import java.util.List;

/**
 * Created by Angela on 4/7/2016.
 */
public interface RatingService {

    public Double findRating(Long id);

    public Rating saveRating(Rating rating);

    public void deleteRating(Long ratingId);

    public Rating findRatingByUserAndSong(Long userId, Long songId);
}
