package com.amadeusounds.service.impl;

import com.amadeusounds.model.Comment;
import com.amadeusounds.repository.CommentRepository;
import com.amadeusounds.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Angela on 4/7/2016.
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> findCommentsForSong(Long songId) {
        return commentRepository.findBySong_id(songId);
    }
    @Override
    public Comment findCommentById(Long commentId) {
        return commentRepository.findOne(commentId);
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.saveAndFlush(comment);
    }

    @Override
    public void deleteComment(Long commentId){
        commentRepository.delete(commentId);
    }
}
