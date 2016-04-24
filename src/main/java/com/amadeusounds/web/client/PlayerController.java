package com.amadeusounds.web.client;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.SongImage;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.repository.SongImageRepository;
import com.amadeusounds.repository.SongRepository;
import com.amadeusounds.service.SongImageService;
import com.amadeusounds.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Vac on 4/13/2016.
 */
@Controller
@RequestMapping("/api/play")
public class PlayerController {

    @Autowired
    SongService songService;

    @Autowired
    SongImageService songImageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void play(@PathVariable(value = "id") long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Song song = songService.findSongById(id);

        // the magic
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Accept-Ranges", "bytes");
        response.setStatus(206);
        int length = (int) song.getSong().length();

        // the logic
        if (request.getHeader("Range") != null) {
            String temp = request.getHeader("Range");
            String[] range = temp.substring(6).split("-");
            boolean hasTwoRanges = false;
            int range1 = Integer.parseInt(range[0]);
            int range2 = 0;
            if (range.length > 1) {
                hasTwoRanges = true;
                range2 = Integer.parseInt(range[1]);
            }

            if (!hasTwoRanges) {
                int remaining = length - range1;
                response.setHeader("Content-Length", String.valueOf(remaining));
                response.setHeader("Content-Range", String.format("bytes %d-%d/%d", range1, length - 1, length));
                byte[] byteArray = song.getSong().getBytes(range1 + 1, remaining);
                response.getOutputStream().write(byteArray);
                response.getOutputStream().close();
                response.flushBuffer();
            } else {
                response.setHeader("Content-Length", String.valueOf(range2 - range1));
                response.setHeader("Content-Range", String.format("bytes %d-%d/%d", range1, range2, length));
                byte[] byteArray = song.getSong().getBytes(range1 + 1, range2 - range1);
                response.getOutputStream().write(byteArray);
                response.getOutputStream().close();
                response.flushBuffer();
            }

        } else {
            response.setHeader("Content-Length", String.valueOf(length));
            response.setHeader("Content-Range", String.format("bytes %d-%d/%d", 0, length - 1, length));
            byte[] byteArray = song.getSong().getBytes(1, length);
            response.getOutputStream().write(byteArray);
            response.getOutputStream().close();
            response.flushBuffer();
        }
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }
}
