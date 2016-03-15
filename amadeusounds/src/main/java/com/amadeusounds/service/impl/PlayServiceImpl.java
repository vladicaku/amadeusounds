package com.amadeusounds.service.impl;

import com.amadeusounds.model.Song;
import com.amadeusounds.repository.SongRepository;
import com.amadeusounds.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
@Service
public class PlayServiceImpl implements PlayService{

    @Autowired
    SongRepository songRepository;

    @Override
    public Song getFirstSong() {
        List<Song> songs = songRepository.findAll();
        if (songs != null && songs.size() > 0) {
            return songs.get(0);
        }
        return null;
    }
}
