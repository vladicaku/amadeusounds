package com.amadeusounds.service.impl;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.SongImage;
import com.amadeusounds.repository.SongImageRepository;
import com.amadeusounds.service.SongImageService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;

/**
 * Created by Vac on 4/13/2016.
 */

@Service
public class SongImageServiceImpl implements SongImageService {
    @Autowired
    SongImageRepository songImageRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public SongImage findSongeImageById(Long id) {
        return songImageRepository.findOne(id);
    }

    @Override
    @Transactional
    public SongImage saveSongImage(SongImage songImage, Song song) {
        songImage.setSong(song);
        songImageRepository.saveAndFlush(songImage);
        return songImage;
    }

    @Override
    @Transactional
    public SongImage updateSongImage(SongImage songImage) {
        songImageRepository.saveAndFlush(songImage);
        return songImage;
    }

    @Override
    @Transactional
    public void addBlobToSongImage(SongImage songImage, MultipartFile multipartFile) throws IOException {
        Blob blob = Hibernate.getLobCreator(getCurrentSession()).createBlob(multipartFile.getInputStream(), multipartFile.getSize());
        songImage.setImage(blob);
        songImageRepository.saveAndFlush(songImage);
    }

    @Override
    public void deleteSongImage(SongImage songImage) {
        songImageRepository.delete(songImage);
    }

    protected Session getCurrentSession()  {
        return entityManager.unwrap(Session.class);
    }
}
