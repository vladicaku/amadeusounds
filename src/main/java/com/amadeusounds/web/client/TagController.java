package com.amadeusounds.web.client;

import com.amadeusounds.model.Category;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.Tag;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.service.SongService;
import com.amadeusounds.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author Vladica Jovanovski
 */

@CrossOrigin()
@RestController(value = "tagsController")
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @Autowired
    SongService songService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Tag> getAllTags()
    {
        return tagService.getAllTags();
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET)
    public void getTagImage(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
    {
        Tag tag = tagService.findTagById(id);
        if (tag != null)
        {
            try {
                OutputStream outputStream = response.getOutputStream();
                byte[] byteArray = tag.getImage().getBytes(1, (int) tag.getImage().length());
                response.setContentType("image/png");
                response.setContentLengthLong(byteArray.length);
                outputStream.write(byteArray);
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TODO
    //To be moved in the admin class
    //Methods for removing and adding tags to song
    //Method for changing category of song
    @RequestMapping(value = "/{tagId}/songs/{songId}", method = RequestMethod.POST)
    public void addTagToSong(@PathVariable Long tagId, @PathVariable Long songId) {

        Song song=songService.findSongById(songId);
        Tag tag=tagService.findTagById(tagId);

        List<Tag> tags=song.getTags();
        if(tags==null){
            tags=new ArrayList<Tag>();
        }
        tags.add(tag);
        song.setTags(tags);
        songService.saveSong(song);

    }
    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }

}
