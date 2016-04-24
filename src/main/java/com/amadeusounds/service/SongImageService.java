package com.amadeusounds.service;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.SongImage;
import com.amadeusounds.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Vac on 4/13/2016.
 */
public interface SongImageService {

    public SongImage findSongImageById(Long id);

    SongImage saveSongImage(SongImage songImage);

    SongImage updateSongImage(SongImage newSongImage) throws Exception;

    void addBlobToSongImage(SongImage songImage, MultipartFile multipartFile) throws IOException;

    public void deleteSongImage(SongImage songImage);

    public void deleteSongImage(Long id);

}
