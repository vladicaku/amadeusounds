package com.amadeusounds.service.impl;

import com.amadeusounds.model.Song;
import com.amadeusounds.repository.SongRepository;
import com.amadeusounds.repository.UserRepository;
import com.amadeusounds.service.UploadService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
@Service
@Repository
public class UploadServiceImpl implements UploadService {
    @Autowired
    SongRepository songRepository;

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    SessionFactory sessionFactory;

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public void uploadFile(String name, String description, MultipartFile file, List<MultipartFile> images) throws IOException {
        Blob songBlob = Hibernate.getLobCreator(getCurrentSession()).createBlob(file.getInputStream(), file.getSize());
        Song song = new Song();
        song.setName(name);
        song.setDescription(description);
        song.setSong(songBlob);
        songRepository.saveAndFlush(song);
    }

    protected Session getCurrentSession()  {
        return entityManager.unwrap(Session.class);
    }
}
