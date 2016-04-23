package com.amadeusounds.service;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Vac on 4/13/2016.
 */
public interface SongService {

    Song findSongById(Long id);

    Song saveSong(Song song);

    Song updateSong(Song song);

    void deleteSong(Song song);

    void addBlobToSong(Song song, MultipartFile multipartFile) throws IOException;

    Page<Song> getAllSongsForUser(User user, Pageable pageable);

    Page<Song> getLatestSongs(Pageable pageable);

    Page<Song> getTrendingSongs(Pageable pageable);

    Page<Song> getTopRatedSongs(Pageable pageable);

    Page<Song> findAllSongs(String term, Pageable pageable);
}
