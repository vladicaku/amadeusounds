package com.amadeusounds.web;

import com.amadeusounds.model.Comment;
import com.amadeusounds.repository.SongRepository;
import com.amadeusounds.repository.UserRepository;
import com.amadeusounds.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Angela on 4/7/2016.
 */
@Controller
@RequestMapping("/api/songs")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    SongRepository songRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Get all comments for a song
     *
     * @param song_id
     * @return list of all comments for a given song
     */

    @RequestMapping(value = "/{song_id}/comments", method = RequestMethod.GET)
    public @ResponseBody List<Comment> findAllComments(@PathVariable Long song_id) {
        return commentService.findCommentsForSong(song_id);
    }

    /**
     * Get a single comment
     *
     * @param song_id
     * @param comment_id
     * @return the comment with the given id
     */
    @RequestMapping(value = "/{song_id}/comments/{comment_id}", method = RequestMethod.GET)
    public @ResponseBody Comment findComment(@PathVariable Long song_id, @PathVariable Long comment_id) {
        return commentService.findCommentById(comment_id);
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
     * @param comment_id
     * @param comment
     * @return the updated comment
     */
    @RequestMapping(value = "/{song_id}/comments/{comment_id}", method = RequestMethod.PUT)
    public @ResponseBody Comment updateComment(@PathVariable Long song_id, @PathVariable Long comment_id, @RequestBody Comment comment) {
        Comment oldComment = commentService.findCommentById(comment_id);
        oldComment.setComment(comment.getComment());
        return commentService.saveComment(oldComment);
    }

    /**
     * Delete a comment with a given id
     *
     * @param song_id
     * @param comment_id
     */

    @RequestMapping(value = "/{song_id}/comments/{comment_id}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable Long song_id, @PathVariable Long comment_id) {
        commentService.deleteComment(comment_id);
    }
}
