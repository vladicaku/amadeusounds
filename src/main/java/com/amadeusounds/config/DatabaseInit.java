package com.amadeusounds.config;

import com.amadeusounds.model.Tag;
import com.amadeusounds.service.CategoryService;
import com.amadeusounds.service.TagService;
import org.apache.log4j.Category;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import sun.security.util.Resources;

import javax.annotation.PostConstruct;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;

/**
 * Description
 *
 * @author Vladica Jovanovski
 */
@Component
public class DatabaseInit implements ApplicationContextAware{

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
        Tag tag = tagService.findTagById((long) 1);
        if (tag == null)
        {
            for (int i=0; i<10; i++)
            {
                Tag t = new Tag();
                t.setName("Tag" + i);
                Resource resource = applicationContext.getResource("classpath:/icons/sun-icon.png");
                InputStream inputStream = resource.getInputStream();
                byte[] byteArray = new byte[inputStream.available()];
                inputStream.read(byteArray);
                t.setImage(new SerialBlob(byteArray));
                tagService.saveTag(t);
            }

            for (int i=0; i<10; i++)
            {
                com.amadeusounds.model.Category category = new com.amadeusounds.model.Category();
                category.setName("Category" + i);
                categoryService.saveCategory(category);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
