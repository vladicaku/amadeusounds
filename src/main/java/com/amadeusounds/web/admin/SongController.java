package com.amadeusounds.web.admin;

import com.amadeusounds.model.Comment;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.SongImage;
import com.amadeusounds.model.User;
import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import com.amadeusounds.service.*;
import com.amadeusounds.view.Views;
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

    @Autowired
    SongImageService songImageService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    AngularAuthService angularAuthService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MappingJacksonValue songs(Pageable pageable) {
        Page<Song> page = songService.getAllSongsForUser(null, pageable);
        final MappingJacksonValue result = new MappingJacksonValue(page);
        result.setSerializationView(Views.SongBaseView.class);
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
    public Response createSong(@RequestBody Song song,@RequestParam String category, @RequestParam String username) {
        User user = angularAuthService.getUser();
        song.setUser(user);
        song.setCategory(categoryService.findByName(category));
        songService.saveSong(song);
        Response response = new Response(ResponseType.OK, song.getId());
        return response;
    }

    /**
     * Upload song file
     *
     * @param songId
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{songId}/upload", method = RequestMethod.POST)
    public Response uploadMultipartFile(@PathVariable("songId") long songId,  @RequestParam("song") MultipartFile multipartFile) throws Exception {
        Song song = songService.findSongById(songId);
        songService.addBlobToSong(song, multipartFile);
        return new Response(ResponseType.OK, "");
    }

    /**
     * Dlete a song
     * @param songId
     * @return
     */
    @RequestMapping(value = "/{songId}", method = RequestMethod.DELETE)
    public Response deleteSong(@PathVariable(value = "songId") Long songId) {
        songService.deleteSong(songId);
        Response response = new Response(ResponseType.OK, "");
        return response;
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

    // ====================================================================================================

    /**
     * Save SongImage
     * This method will return an id for the newly created SongImage.
     * The client should upload the image on this URL:
     * /api/admin/songs/{songId}/images/{id}/upload
     *
     * @param songId
     * @param songImage
     * @return songImageId
     */
    @RequestMapping(value = "/{songId}/images", method = RequestMethod.POST)
    public Response saveSongImage(@PathVariable(value = "songId") long songId, @RequestBody SongImage songImage) {
        Song song = songService.findSongById(songId);
        songImage.setSong(song);
        songImageService.saveSongImage(songImage);
        return new Response(ResponseType.OK, songImage.getId());
    }

    /**
     * Update SongImage
     *
     * @param imageId
     * @param songImage
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{songId}/images/{imageId}",method = RequestMethod.PUT)
    public Response updateSongImage(@PathVariable("songId") Long songId,
                                    @PathVariable("imageId") Long imageId,
                                    @RequestBody SongImage songImage) throws Exception {
        Song song = songService.findSongById(songId);
        songImage.setId(imageId);
        songImage.setSong(song);
        songImageService.updateSongImage(songImage);
        return new Response(ResponseType.OK, "");
    }

    /**
     * Delete SongImage
     *
     * @param songId
     * @param imageId
     * @return
     */
    @RequestMapping(value = "/{songId}/images/{imageId}", method = RequestMethod.DELETE)
    public Response deleteSongImage(@PathVariable("songId") Long songId,
                                    @PathVariable("imageId") Long imageId) {
        songImageService.deleteSongImage(imageId);
        return new Response(ResponseType.OK, "");
    }

    /**
     * Upload SongImage
     *
     * @param songId
     * @param imageId
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/{songId}/images/{imageId}/upload", method = RequestMethod.POST)
    public Response uploadSongImage(@PathVariable("songId") Long songId,
                                    @PathVariable("imageId") Long imageId,
                                    @RequestParam("image") MultipartFile multipartFile) throws IOException {
        SongImage songImage = songImageService.findSongImageById(imageId);
        songImageService.addBlobToSongImage(songImage, multipartFile);
        Response response = new Response(ResponseType.OK, "");
        return response;
    }

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }
}
