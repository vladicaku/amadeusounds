package com.amadeusounds.service;

import com.amadeusounds.model.Category;
import com.amadeusounds.model.Tag;

import java.util.List;

/**
 * Simple Tag Service
 *
 * @author Vladica Jovanovski
 */
public interface TagService {

    Tag findTagById(Long id);

    List<Tag> getAllTags();

    Tag saveTag(Tag tag);
}
