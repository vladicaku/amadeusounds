package com.amadeusounds.service.impl;

import com.amadeusounds.model.Comment;
import com.amadeusounds.model.Song;
import com.amadeusounds.repository.CommentRepository;
import com.amadeusounds.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Angela on 4/7/2016.
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> findCommentsForSong(Song song) {
        return commentRepository.findBySong(song);
    }
    @Override
    public Comment findCommentById(Long commentId) {
        return commentRepository.findOne(commentId);
    }

    @Override
    public Comment saveComment(Comment comment) {
        comment.setDate(LocalDate.now());
        return commentRepository.saveAndFlush(comment);
    }

    @Override
    public void deleteComment(Song song, Comment comment) throws Exception {
        if (comment.getSong() == song) {
            commentRepository.delete(comment);
        } else {
            throw new Exception("Comment not found.");
        }
    }
}
