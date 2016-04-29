package com.amadeusounds.web.admin;

import com.amadeusounds.model.Comment;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.service.CommentService;
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
import java.util.List;

/**
 * SongController
 *
 * @author Vladica Jovanovski
 * @author Angela Josifovska
 */
@CrossOrigin()
@RestController(value = "ApiSongController")
@RequestMapping("/api/admin/songs")
public class SongController {

    @Autowired
    SongService songService;

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MappingJacksonValue songs(Pageable pageable) {
        Page<Song> page = songService.getAllSongsForUser(null, pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(SongView.BaseView.class);
        return result;
    }

    /**
     * This method return the ID from the newly created song.
     * The client should upload the MultipartFile to the following url:
     * /upload/{id}
     * @param song
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Response createSong(@RequestBody Song song) {
        songService.saveSong(song);
        Response response = new Response(ResponseType.OK, song.getId());
        return response;
    }

    @RequestMapping(value = "/{songId}", method = RequestMethod.DELETE)
    public Response deleteSong(@PathVariable(value = "songId") Long songId) {
        songService.deleteSong(songId);
        Response response = new Response(ResponseType.OK, "");
        return response;
    }

    @RequestMapping(value = "/upload/{songId}", method = RequestMethod.POST)
    public Response uploadMultipartFile(@PathVariable("songId") long songId,  @RequestParam("song") MultipartFile multipartFile) throws Exception {
        Song song = songService.findSongById(songId);
        songService.addBlobToSong(song, multipartFile);
        return new Response(ResponseType.OK, "");
    }

    /**
     * Get all comments for a song
     *
     * @param songId
     * @return Response with list of all comments for a given song
     */
    @RequestMapping(value = "/{songId}/comments", method = RequestMethod.GET)
    public Response findAllComments(@PathVariable(value = "songId") Long songId) {
        Song song = songService.findSongById(songId);
        List<Comment> commentList = song.getComments();
        return new Response(ResponseType.OK, commentList);
    }


    /**
     * Post new comment
     *
     * @param songId
     * @param comment
     * @return Response
     */
    @RequestMapping(value = "/{songId}/comments/", method = RequestMethod.POST)
    public Response addNewComment(@PathVariable("songId") Long songId, @RequestBody Comment comment) {
        Song song = songService.findSongById(songId);
        comment.setSong(song);
        comment.setUser(null);
        /**
         * TODO:
         * Get current user from session
         */
        commentService.saveComment(comment);
        return new Response(ResponseType.OK, "");
    }

    /**
     * Delete a comment with a given id
     *
     * @param songId
     * @param commentId
     * @return Response
     */
    @RequestMapping(value = "/{songId}/comments/{commentId}", method = RequestMethod.DELETE)
    public Response deleteComment(@PathVariable("songId") Long songId,
                              @PathVariable("commentId") Long commentId) throws Exception {
        Song song = songService.findSongById(songId);
        Comment comment = commentService.findCommentById(commentId);
        commentService.deleteComment(song, comment);
        return new Response(ResponseType.OK, "");
    }

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }
}
