package com.amadeusounds.web;

import com.amadeusounds.model.Song;
import com.amadeusounds.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
@Controller
@RequestMapping("/play")
public class Play {

    @Autowired
    PlayService playService;

    @RequestMapping(path="/1", method = RequestMethod.GET)
    public HttpEntity<byte[]> play1(HttpServletResponse response) throws IOException, SQLException {
        HttpHeaders header = new HttpHeaders();
        //header.setContentType(new MediaType("audio", "mpeg"));
        header.setContentType(new MediaType("application", "octet-stream"));

        byte[] byteArray = null;

        Song song = playService.getFirstSong();
        if (song != null) {
            byteArray = new byte[(int) song.getSong().length()];
            song.getSong().getBinaryStream().read(byteArray);
        }

        return new HttpEntity<byte[]>(byteArray, header);
    }

    @RequestMapping(path="/2", method = RequestMethod.GET)
    public HttpEntity<byte[]> play2(HttpServletResponse response) throws IOException, SQLException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("audio", "mpeg"));
        byte[] byteArray = null;

        Song song = playService.getFirstSong();
        if (song != null) {
            byteArray = new byte[(int) song.getSong().getBinaryStream().available()];
            song.getSong().getBinaryStream().read(byteArray);
        }

        return new HttpEntity<byte[]>(byteArray, header);
    }

    //@RequestMapping(path="/{id}", method = RequestMethod.GET)
    //public void play3(@PathVariable("id") String id, HttpServletResponse response, HttpServletRequest request) throws IOException, SQLException {
    @RequestMapping(method = RequestMethod.GET)
    public void play3(HttpServletResponse response, HttpServletRequest request) throws IOException, SQLException {
        Song song = playService.getFirstSong();
        if (song != null) {
            response.reset();
            // the magic
            response.setContentType("application/octet-stream");
            response.setHeader("Accept-Ranges", "bytes");
            response.setStatus(206);
            int length = (int) song.getSong().length();

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
    }
}
