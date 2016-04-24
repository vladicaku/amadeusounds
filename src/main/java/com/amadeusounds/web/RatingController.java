//package com.amadeusounds.web;
//
//import com.amadeusounds.model.Rating;
//import com.amadeusounds.repository.SongRepository;
//import com.amadeusounds.repository.UserRepository;
//import com.amadeusounds.service.RatingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
///**
// * Created by Angela on 4/8/2016.
// */
//
//@Controller
//@RequestMapping("/api/rating")
//public class RatingController {
//
//    @Autowired
//    RatingService ratingService;
//
//    @Autowired
//    SongRepository songRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    /**
//     * Find average rating for a given song
//     *
//     * @param song_id
//     * @return calculated rating for a given song
//     */
//
//    @RequestMapping(value = "/{song_id}/rating", method = RequestMethod.GET)
//    public @ResponseBody Double findAvgRating(@PathVariable Long song_id) {
//        return ratingService.findRating(song_id);
//    }
//
//    /**
//     * Add new rating or update if rating already exist
//     * @param song_id
//     * @param rating
//     * @param user_id
//     * @return new or updated rating
//     */
//
//    @RequestMapping(value = "/{song_id}/rating/{user_id}", method = RequestMethod.POST)
//    public @ResponseBody Rating addRating(@PathVariable Long song_id, @RequestBody Rating rating, @PathVariable Long user_id) {
//
//        Rating oldRating=null;
//        try{
//            oldRating=ratingService.findRatingByUserAndSong(user_id,song_id);
//        }catch(Exception ex){
//            //do nothing
//        }
//        if(oldRating!=null){
//            oldRating.setRating(rating.getRating());
//            return ratingService.saveRating(oldRating);
//        }else{
//            rating.setSong(songRepository.findOne(song_id));
//            rating.setUser(userRepository.findOne(user_id));
//            return ratingService.saveRating(rating);
//        }
//
//    }
//
//    /**
//     *
//     * @param song_id
//     * @param rating_id
//     */
//    @RequestMapping(value = "/{song_id}/rating/{rating_id}", method = RequestMethod.DELETE)
//    public void deleteComment(@PathVariable Long song_id, @PathVariable Long rating_id) {
//        ratingService.deleteRating(rating_id);
//    }
//}
