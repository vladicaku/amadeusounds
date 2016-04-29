package com.amadeusounds.web.test;

import com.amadeusounds.model.Rating;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.repository.RatingRepository;
import com.amadeusounds.service.RatingService;
import com.amadeusounds.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;

/**
 * Created by Vac on 4/24/2016.
 */
@RestController
@RequestMapping("/create-records")
public class CreateRecords {

    @Autowired
    SongService songService;

    @Autowired
    RatingService ratingService;

    Random random = new Random();

    @RequestMapping(method = RequestMethod.GET)
    public Response createRecords() {

        for(int i=0; i<10; i++) {
            Song song = new Song();
            song.setName(i + "ta pesna");
            song.setDate(LocalDate.now().minusDays(i));
            songService.saveSong(song);

            int numberOfComments = random.nextInt(6) + 1;
            for (int j=0; j<numberOfComments; j++) {
                int ratingInt = random.nextInt(5) + 1;

                Rating rating = new Rating();
                rating.setSong(song);
                rating.setDate(LocalDate.now());
                rating.setRating(ratingInt);
                ratingService.saveRating(rating);
            }
        }

        return new Response(ResponseType.OK, "The database is populated");
    }

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }
}
