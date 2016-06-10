package com.amadeusounds.web.client;

import com.amadeusounds.model.*;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.service.*;
import com.amadeusounds.view.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * SongController
 *
 * @author Vladica Jovanovski
 * @author Angela Josifovska
 */
@CrossOrigin()
@RestController(value = "clientSongController")
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    SongService songService;

    @Autowired
    CommentService commentService;

    @Autowired
    RatingService ratingService;

    @Autowired
    UserService userService;

    @Autowired
    SongImageService songImageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MappingJacksonValue getSong(@PathVariable(value = "id") long id) {
        Song song = songService.findSongById(id);
        final MappingJacksonValue result = new MappingJacksonValue(song);
        result.setSerializationView(Views.SongSummaryView.class);
        return result;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public MappingJacksonValue getAllSongsForUser(@PathVariable("userId") long id, Pageable pageable) {
        User user = userService.findUserById(id);
        Page<Song> page = songService.getAllSongsForUser(user, pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(Views.SongSummaryView.class);
        return result;
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public List<Comment> findAllComments(@PathVariable("id") Long id) {
        Song song = songService.findSongById(id);
        return commentService.findCommentsForSong(song);
    }

    /**
     * Get rating for a given song
     *
     * @param songId
     * @return calculated rating for a given song
     */
    @RequestMapping(value = "/{songId}/rating", method = RequestMethod.GET)
    public Response getRating(@PathVariable("songId") Long songId) {
        Song song = songService.findSongById(songId);
        return new Response(ResponseType.OK, song.getRating());
    }

    /**
     * Add (or change) rating to a song
     *
     * @param songId
     * @param rating
     * @return Response
     */
    @RequestMapping(value = "/{songId}/rating/", method = RequestMethod.POST)
    public Response rateSong(@PathVariable("songId") Long songId, @RequestBody Rating rating) {
        /**
         * TODO:
         * Get current user from session
         */
        User user = null;
        Song song = songService.findSongById(songId);
        rating.setSong(song);
        rating.setUser(user);
        ratingService.rate(rating);
        return new Response(ResponseType.OK, "");
    }

    /**
     * Get a image for a song
     *
     * @param songId
     * @param imageId
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/{songId}/image/{imageId}", method = RequestMethod.GET)
    public void getImage(@PathVariable(value = "songId") long songId,
                         @PathVariable(value = "imageId") long imageId,
                         HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        Song song = songService.findSongById(songId);
        SongImage songImage = songImageService.findSongImageById(imageId);

        if (song == songImage.getSong()) {
            OutputStream outputStream = response.getOutputStream();
            byte[] byteArray = songImage.getImage().getBytes(1, (int) songImage.getImage().length());
            outputStream.write(byteArray);
            outputStream.flush();
        } else {
            throw new Exception("Image not found");
        }
    }

    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    public Page<Song> getLatestSongs(Pageable pageable) {
        Page<Song> page = songService.getLatestSongs(pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(Views.SongSummaryView.class);
        return page;
    }

    @RequestMapping(value = "/trending", method = RequestMethod.GET)
    public MappingJacksonValue getTrendingSongs(Pageable pageable) {
        Page<Song> page = songService.getTrendingSongs(pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(Views.SongSummaryView.class);
        return result;
    }

    @RequestMapping(value = "/top-rated", method = RequestMethod.GET)
    public MappingJacksonValue getTopRatedSongs(Pageable pageable) {
        Page<Song> page = songService.getTopRatedSongs(pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(Views.SongSummaryView.class);
        return result;
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public MappingJacksonValue findAllSongs(@PathVariable(value = "term") String term, Pageable pageable) {
        Page<Song> page = songService.findAllSongs(term, pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(Views.SongSummaryView.class);
        return result;
    }

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }
}
