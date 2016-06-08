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
        String[] categories = new String[]{"Blues", "Classic", "Country", "Disco",
                "Funk", "Hip-Hop","Jazz", "Metal", "Pop", "R&B", "Rap","Reggae", "Rock","Techno",
                "Alternative","House","Soul","Punk","Electronic","Pop-Folk","Retro","Latin", "other"};

        List<Tag> tags= tagService.getAllTags();
//        if (tags.size()==0)
//        {
//            Tag t=new Tag();
//            t.setName("Running");
//            t.setImage(getBlobFromImage("running"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Party");
//            t.setImage(getBlobFromImage("party"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Relax");
//            t.setImage(getBlobFromImage("relax"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Studying");
//            t.setImage(getBlobFromImage("studying"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Workout");
//            t.setImage(getBlobFromImage("workout"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Children");
//            t.setImage(getBlobFromImage("children"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Motivation");
//            t.setImage(getBlobFromImage("motivational"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Dance");
//            t.setImage(getBlobFromImage("dance"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Romantic");
//            t.setImage(getBlobFromImage("romantic"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Concentration");
//            t.setImage(getBlobFromImage("concentrating"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Summer");
//            t.setImage(getBlobFromImage("summer"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Teenage");
//            t.setImage(getBlobFromImage("teenage"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Fast");
//            t.setImage(getBlobFromImage("fast"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Christmas");
//            t.setImage(getBlobFromImage("christmas"));
//            tagService.saveTag(t);
//
//            t=new Tag();
//            t.setName("Chill");
//            t.setImage(getBlobFromImage("chill"));
//            tagService.saveTag(t);
//
//        }
        List<Category> categoriesList=categoryService.getAllCategories();
        if(categoriesList.size()==0){
            for (int i=0; i<categories.length; i++)
            {
                com.amadeusounds.model.Category category = new com.amadeusounds.model.Category();
                category.setName(categories[i]);
                categoryService.saveCategory(category);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private SerialBlob getBlobFromImage(String imageName) throws IOException, SQLException {
        Resource resource = applicationContext.getResource("classpath:/static/images/"+imageName+".jpg");
        InputStream inputStream = resource.getInputStream();
        byte[] byteArray = new byte[inputStream.available()];
        inputStream.read(byteArray);
        return new SerialBlob(byteArray);
    }
}
