package com.amadeusounds.service.impl;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import com.amadeusounds.repository.BaseRepository;
import com.amadeusounds.repository.SongRepository;
import com.amadeusounds.service.SongService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Vac on 4/13/2016.
 */
@Service
public class SongServiceImpl implements SongService {
    @Autowired
    SongRepository songRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    BaseRepository baseRepository;


    @Override
    public Song findSongById(Long id) {
        return songRepository.findOne(id);
    }

    @Override
    public void saveSong(Song song) {
        if (song.getDate() == null) {
            song.setDate(LocalDate.now());
        }

        if (song.getSong() == null) {
            Blob songBlob = Hibernate.getLobCreator(getCurrentSession()).createBlob(new byte[0]);
            song.setSong(songBlob);
        }

        songRepository.saveAndFlush(song);
    }

    @Override
    public void updateSong(Song song) {
        songRepository.saveAndFlush(song);
    }

    @Override
    public void deleteSong(Song song) {
        songRepository.delete(song);
    }

    @Override
    public void addBlobToSong(Song song, MultipartFile multipartFile) throws IOException {
        Blob songBlob = Hibernate.getLobCreator(getCurrentSession()).createBlob(multipartFile.getInputStream(), multipartFile.getSize());
        song.setSong(songBlob);
        songRepository.saveAndFlush(song);
    }

    @Override
    public Page<Song> getAllSongsForUser(User user, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Song> getLatestSongs(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Song> getTrendingSongs(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Song> getTopRatedSongs(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Song> findAllSongs(String term, Pageable pageable) {
        return null;
    }


    public Page<Song> getSongsForUser(User user) {
        return null;
    }

    protected Session getCurrentSession()  {
        return entityManager.unwrap(Session.class);
    }
}
