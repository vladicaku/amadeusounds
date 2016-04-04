package com.amadeusounds.service;

import com.amadeusounds.model.Song;

import java.sql.Blob;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
public interface PlayService {
    Song getFirstSong();
}
