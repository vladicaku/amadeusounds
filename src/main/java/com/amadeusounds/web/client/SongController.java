package com.amadeusounds.web.client;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.repository.SongRepository;
import com.amadeusounds.repository.UserRepository;
import com.amadeusounds.service.SongService;
import com.amadeusounds.view.SongView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Vac on 4/13/2016.
 */
@CrossOrigin()
@RestController(value = "clientSongController")
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    SongService songService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MappingJacksonValue getSong(@PathVariable(value = "id") long id) {
        Song song = songService.findSongById(id);
        final MappingJacksonValue result = new MappingJacksonValue(song);
        result.setSerializationView(SongView.BaseView.class);
        return result;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public MappingJacksonValue getAllSongsForUser(@PathVariable(value = "userId") long id, Pageable pageable) {
        User user = null;
        Page<Song> page = songService.getAllSongsForUser(user, pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(SongView.BaseView.class);
        return result;
    }

    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    public Page<Song> getLatestSongs(Pageable pageable) {
        Page<Song> page = songService.getLatestSongs(pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(SongView.BaseView.class);
        return page;
    }

    @RequestMapping(value = "/trending", method = RequestMethod.GET)
    public MappingJacksonValue getTrendingSongs(Pageable pageable) {
        Page<Song> page = songService.getTrendingSongs(pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(SongView.BaseView.class);
        return result;
    }

    @RequestMapping(value = "/top-rated", method = RequestMethod.GET)
    public MappingJacksonValue getTopRatedSongs(Pageable pageable) {
        Page<Song> page = songService.getTopRatedSongs(pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(SongView.BaseView.class);
        return result;
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public MappingJacksonValue findAllSongs(@PathVariable(value = "term") String term, Pageable pageable) {
        Page<Song> page = songService.findAllSongs(term, pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(SongView.BaseView.class);
        return result;
    }

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }
}
