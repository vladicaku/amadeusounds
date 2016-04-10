package com.amadeusounds.repository;

import com.amadeusounds.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Angela on 4/7/2016.
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{

    public List<Comment> findBySong_id(Long songId);
}
