package com.amadeusounds.service;

import com.amadeusounds.model.Category;

import java.util.List;

/**
 * Simple Category Service
 *
 * @author Vladica Jovanovski
 */
public interface CategoryService {

    Category findByCategoryId(Long id);

    List<Category> getAllCategories();

    Category saveCategory(Category category);

    Category findByName(String name);
}
