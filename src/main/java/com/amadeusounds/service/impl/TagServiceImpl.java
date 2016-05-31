package com.amadeusounds.service.impl;

import com.amadeusounds.model.Tag;
import com.amadeusounds.repository.TagRepository;
import com.amadeusounds.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description
 *
 * @author Vladica Jovanovski
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public Tag findTagById(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public List<Tag> getAllTags() {
        return  tagRepository.findAll();
    }
}
