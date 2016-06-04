package com.amadeusounds.service.impl;

import com.amadeusounds.model.Category;
import com.amadeusounds.repository.CategoryRepository;
import com.amadeusounds.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description
 *
 * @author Vladica Jovanovski
 */
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return  categoryRepository.saveAndFlush(category);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
