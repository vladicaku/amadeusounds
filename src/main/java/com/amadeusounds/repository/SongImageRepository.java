package com.amadeusounds.repository;

import com.amadeusounds.model.SongImage;
import org.springframework.stereotype.Repository;

/**
 * Created by Vladica Jovanovski on 4/13/2016.
 */
@Repository
public interface SongImageRepository extends JpaSpecificationRepository<SongImage, Long> {
}
