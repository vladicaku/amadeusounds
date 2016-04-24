package com.amadeusounds.web.admin;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.service.SongService;
import com.amadeusounds.view.SongView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Vac on 4/13/2016.
 */
@CrossOrigin()
@RestController(value = "ApiSongController")
@RequestMapping("/admin/songs")
public class SongController {

    @Autowired
    SongService songService;


    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MappingJacksonValue songs(Pageable pageable) {
        Page<Song> page = songService.getAllSongsForUser(null, pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(SongView.BaseView.class);
        return result;
    }

    /**
     * This method return the ID from the newly created song.
     * The client then should upload the MultipartFile to the following url:
     * /upload/ID
     * @param song
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Response createSong(@RequestBody Song song) {
        songService.saveSong(song);
        Response response = new Response(ResponseType.OK, song.getId());
        return response;
    }

    @RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
    public Response uploadMultipartFile(@PathVariable long id, @RequestParam("song") MultipartFile multipartFile) throws IOException {
        Song song = songService.findSongById(id);
        songService.addBlobToSong(song, multipartFile);
        Response response = new Response(ResponseType.OK, "");
        return response;
    }

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }
}
