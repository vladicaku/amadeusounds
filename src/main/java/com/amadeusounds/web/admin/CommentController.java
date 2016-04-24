package com.amadeusounds.web.admin;

import com.amadeusounds.model.Comment;
import com.amadeusounds.model.Song;
import com.amadeusounds.repository.SongRepository;
import com.amadeusounds.repository.UserRepository;
import com.amadeusounds.service.CommentService;
import com.amadeusounds.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Angela on 4/7/2016.
 */
@Controller
@RequestMapping("/api/admin/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    SongService songService;

    @Autowired
    SongRepository songRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Get all comments for a song
     *
     * @param songId
     * @return list of all comments for a given song
     */

    @RequestMapping(value = "/{songId}/comments", method = RequestMethod.GET)
    public @ResponseBody List<Comment> findAllComments(@PathVariable(value = "songId") Long songId) {
        Song song = songService.findSongById(songId);
        return commentService.findCommentsForSong(song);
    }

    /**
     * Get a single comment
     *
     * @param songId
     * @param commentId
     * @return the comment with the given id
     */
    @RequestMapping(value = "/{songId}/comments/{commentId}", method = RequestMethod.GET)
    public @ResponseBody Comment findComment(@PathVariable(value = "songId") Long songId,
                                             @PathVariable(value = "commentId") Long commentId) {
        return commentService.findCommentById(commentId);
    }

    /**
     * Post new comment
     *
     * @param song_id
     * @param comment
     * @param user_id
     * @return the new comment
     */

    @RequestMapping(value = "/{song_id}/comments/{user_id}", method = RequestMethod.POST)
    public @ResponseBody Comment addNewComment(@PathVariable Long song_id, @RequestBody Comment comment, @PathVariable Long user_id) {
        comment.setSong(songRepository.findOne(song_id));
        comment.setUser(userRepository.findOne(user_id));
        return commentService.saveComment(comment);
    }

    /**
     * Update existing comment
     *
     * @param commentId
     * @param comment
     * @return the updated comment
     */
    @RequestMapping(value = "/{songId}/comments/{commentId}", method = RequestMethod.PUT)
    public @ResponseBody Comment updateComment(@PathVariable(value = "songId") Long songId,
                                               @PathVariable(value = "commentId") Long commentId,
                                               @RequestBody Comment comment) {
        Comment oldComment = commentService.findCommentById(commentId);
        oldComment.setComment(comment.getComment());
        return commentService.saveComment(oldComment);
    }

    /**
     * Delete a comment with a given id
     *
     * @param songId
     * @param commentId
     */

    @RequestMapping(value = "/{songId}/comments/{commentId}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable(value = "songId") Long songId,
                              @PathVariable(value = "commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }
}
