package com.amadeusounds.web.api;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.SongImage;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.service.SongImageService;
import com.amadeusounds.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Vac on 4/13/2016.
 */
@RestController(value = "apiSongImageController")
@RequestMapping("api/images")
public class SongImageController {

    @Autowired
    SongService songService;

    @Autowired
    SongImageService songImageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Response saveSongImage(@PathVariable long id, SongImage songImage) {
        Song song = songService.findSongById(id);
        songImageService.saveSongImage(songImage, song);
        return new Response(ResponseType.OK, "");
    }


    @RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
    public Response uploadSongImage(@PathVariable long id, @RequestParam("image") MultipartFile multipartFile) throws IOException {
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
