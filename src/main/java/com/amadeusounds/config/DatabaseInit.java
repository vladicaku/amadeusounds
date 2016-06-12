package com.amadeusounds.config;

import com.amadeusounds.model.Category;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.Tag;
import com.amadeusounds.model.User;
import com.amadeusounds.repository.CategoryRepository;
import com.amadeusounds.repository.SongRepository;
import com.amadeusounds.repository.TagRepository;
import com.amadeusounds.repository.UserRepository;
import com.amadeusounds.service.CategoryService;
import com.amadeusounds.service.TagService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sun.security.util.Resources;

import javax.annotation.PostConstruct;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Database init
 *
 * @author Vladica Jovanovski
 */
@Component
public class DatabaseInit implements ApplicationContextAware {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    ApplicationContext applicationContext;

    /**
     * Fill the database
     */
    @PostConstruct
    public void init() throws IOException, SQLException {
        createUsers();
        createCategories();
        createTags();
        createSongs();
        createDefoltUserImage();
    }

    private void createSongs() throws IOException, SQLException {
        List<Song> songs = songRepository.findAll();
        if (songs.size() > 0) {
            return;
        }
        User user = userRepository.findByEmail("vladica@gmail.com");
        Category category = categoryRepository.findByName("Disco");
        Song song = new Song();
        song.setName("Prva");
        song.setUser(user);
        song.setCategory(category);
        song.setTags(new ArrayList<>());
        song.getTags().add(tagRepository.findByName("summer"));
        song.getTags().add(tagRepository.findByName("party"));
        song.setSong(getBlobFromSong("1"));
        song.setDate(LocalDate.now());
        songRepository.saveAndFlush(song);

        user = userRepository.findByEmail("slavko@gmail.com");
        category = categoryRepository.findByName("Pop");
        song = new Song();
        song.setName("Vtora");
        song.setUser(user);
        song.setCategory(category);
        song.setTags(new ArrayList<>());
        song.getTags().add(tagRepository.findByName("coffee"));
        song.getTags().add(tagRepository.findByName("relax"));
        song.setSong(getBlobFromSong("2"));
        song.setDate(LocalDate.now());
        songRepository.saveAndFlush(song);

        user = userRepository.findByEmail("angela@gmail.com");
        category = categoryRepository.findByName("Disco");
        song = new Song();
        song.setName("House");
        song.setUser(user);
        song.setCategory(category);
        song.setTags(new ArrayList<>());
        song.getTags().add(tagRepository.findByName("dj"));
        song.getTags().add(tagRepository.findByName("running"));
        song.setSong(getBlobFromSong("1"));
        song.setDate(LocalDate.now());
        songRepository.saveAndFlush(song);

    }

    private void createTags() throws IOException, SQLException {
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
        List<Tag> tagList = tagRepository.findAll();
        if (tagList.size() == 0) {
            //String a = DatabaseInit.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            for (String s : tags) {
                Tag tag = new Tag();
                String tagName = s.substring(0, 1).toUpperCase() + s.substring(1);
                tag.setName(tagName);
                Resource resource = applicationContext.getResource("classpath:/tags/medium/" + s + ".png");
                InputStream inputStream = resource.getInputStream();
                byte[] byteArray = new byte[inputStream.available()];
                inputStream.read(byteArray);
                tag.setImage(new SerialBlob(byteArray));
                tagRepository.saveAndFlush(tag);
            }
        }
    }


    private void createCategories() {
        String[] categories = new String[]{"Blues", "Classic", "Country", "Disco",
            "Funk", "Hip-Hop", "Jazz", "Metal", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno",
            "Alternative", "House", "Soul", "Punk", "Electronic", "Pop-Folk", "Retro", "Latin", "Other"};

        List<Category> categoriesList = categoryRepository.findAll();
        if (categoriesList.size() == 0) {
            for (int i = 0; i < categories.length; i++) {
                com.amadeusounds.model.Category category = new com.amadeusounds.model.Category();
                category.setName(categories[i]);
                categoryRepository.saveAndFlush(category);
            }
        }
    }

    private void createUsers() {
        if (userRepository.findByEmail("vladica@gmail.com") == null) {
            User user = new User();
            user.setActive(true);
            user.setFirstName("Vladica");
            user.setLastName("Jovanovski");
            user.setEmail("vladica@gmail.com");
            user.setPassword(passwordEncoder.encode("pass123"));
            user.setToken(passwordEncoder.encode(user.getFirstName() + user.getLastName()));
            userRepository.saveAndFlush(user);
            System.out.println("User Vladica created.");
        }

        if (userRepository.findByEmail("slavko@gmail.com") == null) {
            User user = new User();
            user.setActive(true);
            user.setFirstName("Slavko");
            user.setLastName("Kazakov");
            user.setEmail("slavko@gmail.com");
            user.setPassword(passwordEncoder.encode("pass123"));
            user.setToken(passwordEncoder.encode(user.getFirstName() + user.getLastName()));
            userRepository.saveAndFlush(user);
            System.out.println("User Slavko created.");
        }

        if (userRepository.findByEmail("angela@gmail.com") == null) {
            User user = new User();
            user.setActive(true);
            user.setFirstName("Angela");
            user.setLastName("Josifovska");
            user.setEmail("angela@gmail.com");
            user.setPassword(passwordEncoder.encode("pass123"));
            user.setToken(passwordEncoder.encode(user.getFirstName() + user.getLastName()));
            userRepository.saveAndFlush(user);
            System.out.println("User Angela created.");
        }

    }

    private void createDefoltUserImage() throws IOException, SQLException{
        if (userRepository.findByEmail("defolt@gmail.com") == null) {
            User user = new User();
            user.setActive(true);
            user.setFirstName("Defolt");
            user.setLastName("Image");
            user.setEmail("defolt@gmail.com");
            user.setPassword(passwordEncoder.encode("pass123"));
            user.setToken(passwordEncoder.encode(user.getFirstName() + user.getLastName()));

            Resource resource = applicationContext.getResource("classpath:/tags/medium/profile_default.png");
            InputStream inputStream = resource.getInputStream();
            byte[] byteArray = new byte[inputStream.available()];
            inputStream.read(byteArray);
            user.setImage(new SerialBlob(byteArray));

            userRepository.saveAndFlush(user);
            System.err.println("Defolt User Image created.");
        }
    }


    private SerialBlob getBlobFromSong(String songName) throws IOException, SQLException {
        Resource resource = applicationContext.getResource("classpath:/songs/" + songName + ".mp3");
        InputStream inputStream = resource.getInputStream();
        byte[] byteArray = new byte[inputStream.available()];
        inputStream.read(byteArray);
        return new SerialBlob(byteArray);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
