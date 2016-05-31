package com.amadeusounds.web.client;

import com.amadeusounds.model.Category;
import com.amadeusounds.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description
 *
 * @author Vladica Jovanovski
 */
@CrossOrigin()
@RestController(value = "categoryController")
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

}
