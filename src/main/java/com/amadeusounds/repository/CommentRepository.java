package com.amadeusounds.repository;

import com.amadeusounds.model.Comment;
import com.amadeusounds.model.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Vac on 4/24/2016.
 */
@Repository
public interface CommentRepository extends JpaSpecificationRepository<Comment, Long>{
    public List<Comment> findBySong(Song song);
}
