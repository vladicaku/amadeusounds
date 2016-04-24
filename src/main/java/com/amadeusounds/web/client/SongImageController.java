package com.amadeusounds.web.client;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.SongImage;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.service.SongImageService;
import com.amadeusounds.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by Vac on 4/24/2016.
 */
@RestController(value = "SongImageController")
@RequestMapping("/api/images")
public class SongImageController {

    @Autowired
    SongService songService;

    @Autowired
    SongImageService songImageService;

    @RequestMapping(value = "/{songId}/image/{imageId}", method = RequestMethod.GET)
    public void getImage(@PathVariable(value = "songId") long songId,
                         @PathVariable(value = "imageId") long imageId,
                         HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        Song song = songService.findSongById(songId);
        SongImage songImage = songImageService.findSongImageById(imageId);

        if (song == songImage.getSong()) {
            OutputStream outputStream = response.getOutputStream();
            byte[] byteArray = songImage.getImage().getBytes(0, (int) songImage.getImage().length());
            outputStream.write(byteArray);
            outputStream.flush();
        } else {
            throw new Exception("Image not found");
        }
    }

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }
}
