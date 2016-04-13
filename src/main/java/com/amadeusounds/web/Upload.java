package com.amadeusounds.web;

import com.amadeusounds.model.json.Response;
import com.amadeusounds.model.json.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */
@RestController
@RequestMapping("/user/upload")
public class Upload {

    @Autowired
    UploadService uploadService;

    @RequestMapping(method = RequestMethod.GET)
    public Response hello() {
        return new Response(ResponseType.OK, "Hello world");
    }


    @RequestMapping(method = RequestMethod.POST)
    public Response upload(@RequestParam("name") String name, @RequestParam("description") String description,
                           @RequestParam("song") MultipartFile song) throws IOException {
        uploadService.uploadFile(name, description, song, null);

        Response response = new Response(ResponseType.OK, "File uploaded successfully");
        return response;
    }

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return new Response(ResponseType.ERROR, e.getMessage());
    }
}
