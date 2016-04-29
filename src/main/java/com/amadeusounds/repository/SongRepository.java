package com.amadeusounds.repository;

import com.amadeusounds.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
@Repository
public interface SongRepository extends JpaSpecificationRepository<Song, Long> {
}
