package com.amadeusounds.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
public interface UploadService {
    public void uploadFile(String name, String description, MultipartFile file, List<MultipartFile> images) throws IOException;
}
