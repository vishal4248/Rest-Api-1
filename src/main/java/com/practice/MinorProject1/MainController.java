package com.practice.MinorProject1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class MainController {

    /**
     * 1.)
     * Create a REST API which takes in a zip file from the client and unzips the content of that zip file.
     *  Note : Zip file can be a directory or can be combination of multiple files/directories
     */

    @Autowired
    UnzipService unzipService;

    @PostMapping("uploadZipFile")
    public String  uploadZipFile(HttpServletRequest httpServletRequest) {
        return unzipService.unzippingOfFile(httpServletRequest);
    }


    /**
     * 2.)
     * Create a REST API that takes in an integer id and
     * returns the particular image from this website https://picsum.photos/ to the client.
     */

    @Autowired
    GetImageService getImageService;

    @GetMapping(value = "/getImage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageById(@PathVariable("id") int id) {
        return getImageService.getImageFromUrl(id);
    }


    /**
     * 3.)
     * Create a REST API which takes in a list of integer ids from client and
     * return a zip file containing all the images which particular id
     * from this website - https://picsum.photos/
     *
     */

    @GetMapping("/getImagesZip")
    public void getImagesZip(@RequestParam("ids") String ids, HttpServletResponse httpServletResponse) {
        getImageService.getMultipleImages(ids,httpServletResponse);
    }

}
