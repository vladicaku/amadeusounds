package com.amadeusounds.web.admin;

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
@CrossOrigin()
@RestController(value = "ApiSongImageController")
@RequestMapping("/api/admin/images")
public class SongImageController {

    @Autowired
    SongService songService;

    @Autowired
    SongImageService songImageService;

    /**
     * Save SongImage
     * This method will return an id for the newly created SongImage.
     * The client should upload the image on this URL:
     * /api/admin/images/upload/{id}
     *
     * @param songId
     * @param songImage
     * @return songImageId
     */
    @RequestMapping(value = "/song/{songId}", method = RequestMethod.POST)
    public Response saveSongImage(@PathVariable(value = "songId") long songId, @RequestBody SongImage songImage) {
        Song song = songService.findSongById(songId);
        songImage.setSong(song);
        songImageService.saveSongImage(songImage);
        return new Response(ResponseType.OK, songImage.getId());
    }

    /**
     * Update SongImage
     *
     * @param id
     * @param songImage
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Response updateSongImage(@PathVariable(value = "id") Long id, @RequestBody SongImage songImage) throws Exception {
        songImage.setId(id);
        songImageService.updateSongImage(songImage);
        return new Response(ResponseType.OK, "");
    }

    /**
     * Delete SongImage
     *
     * @param id
     * @return Response
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response deleteSongImage(@PathVariable(value = "id") Long id) {
        songImageService.deleteSongImage(id);
        return new Response(ResponseType.OK, "");
    }

    /**
     * Upload SongImage
     *
     * @param id
     * @param multipartFile
     * @return Response
     * @throws IOException
     */
    @RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
    public Response uploadSongImage(@PathVariable(value = "id") long id, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        SongImage songImage = songImageService.findSongImageById(id);
        songImageService.addBlobToSongImage(songImage, multipartFile);
        Response response = new Response(ResponseType.OK, "");
        return response;
    }

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }
}
