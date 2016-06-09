package com.amadeusounds.config;

import com.amadeusounds.model.Category;
import com.amadeusounds.model.Tag;
import com.amadeusounds.service.CategoryService;
import com.amadeusounds.service.TagService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import sun.security.util.Resources;

import javax.annotation.PostConstruct;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;

/**
 * Description
 *
 * @author Vladica Jovanovski
 */
@Component
public class DatabaseInit implements ApplicationContextAware {

    @Autowired
    TagService tagService;

    @Autowired
    CategoryService categoryService;

    ApplicationContext applicationContext;

    /**
     * Fill the database with default categories and tags (no images for now)
     */
    @PostConstruct
    public void init() throws IOException, SQLException {
        String[] categories = new String[]{"Blues", "Classic", "Country", "Disco",
                "Funk", "Hip-Hop", "Jazz", "Metal", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno",
                "Alternative", "House", "Soul", "Punk", "Electronic", "Pop-Folk", "Retro", "Latin", "other"};

        List<Category> categoriesList = categoryService.getAllCategories();
        if (categoriesList.size() == 0) {
            for (int i = 0; i < categories.length; i++) {
                com.amadeusounds.model.Category category = new com.amadeusounds.model.Category();
                category.setName(categories[i]);
                categoryService.saveCategory(category);
            }
        }

        String[] tags = new String[]{
                "bath",
                "beach",
                "car",
                "christmas",
                "city",
                "coffee",
                "cold",
                "concentration",
                "couple",
                "dance",
                "dj",
                "fast",
                "favorite",
                "food",
                "happy",
                "house",
                "kids",
                "love",
                "moon",
                "motivation",
                "music",
                "nature",
                "night",
                "palm",
                "party",
                "rain",
                "relax",
                "river",
                "road",
                "romantic",
                "running",
                "sad",
                "snow",
                "sofa",
                "study",
                "summer",
                "sun",
                "sunset",
                "tea",
                "travel",
                "winter",
                "work",
                "workout"

        };
        List<Tag> tagList = tagService.getAllTags();
        if (tagList.size() == 0) {
            //String a = DatabaseInit.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            for (String s : tags) {
                createTag(s);
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void createTag(String name) throws IOException, SQLException{
        Tag tag = new Tag();
        String tagName = name.substring(0, 1).toUpperCase() + name.substring(1);
        tag.setName(tagName);
        Resource resource = applicationContext.getResource("classpath:/tags/medium/" + name + ".png");
        InputStream inputStream = resource.getInputStream();
        byte[] byteArray = new byte[inputStream.available()];
        inputStream.read(byteArray);
        tag.setImage(new SerialBlob(byteArray));
        tagService.saveTag(tag);

    }
    private SerialBlob getBlobFromImage(String imageName) throws IOException, SQLException {
        Resource resource = applicationContext.getResource("classpath:/static/images/" + imageName + ".jpg");
        InputStream inputStream = resource.getInputStream();
        byte[] byteArray = new byte[inputStream.available()];
        inputStream.read(byteArray);
        return new SerialBlob(byteArray);
    }
}
