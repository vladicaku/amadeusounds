package com.amadeusounds.service;

import com.amadeusounds.model.Comment;
import com.amadeusounds.model.Song;

import java.util.List;

/**
 * Created by Angela on 4/7/2016.
 */
public interface CommentService {

    public List<Comment> findCommentsForSong(Song song);

    public Comment findCommentById(Long commentId);

    public Comment saveComment(Comment comment);

    public void deleteComment(Long commentId);


}
