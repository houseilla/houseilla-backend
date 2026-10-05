package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.model.AdvertisementImage;
import com.houseilla.houseillabackend.service.AdvertisementImageServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class AdvertisementImageController {

    @Autowired
    AdvertisementImageServices advertisementImageServices;

    @PostMapping(value = "/upload_advimages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> saveAdvrtisementImages(
            @RequestParam("advertisementId") int advertisementId,
            @RequestParam("files") MultipartFile[] files)  {

        try {
            // Process each file
            for (MultipartFile file : files) {
                advertisementImageServices.saveAdvetisementImages(advertisementId, file);
                log.info("file : " + file + " saved successfully" );
            }
            return ResponseEntity.ok(Map.of("status", "success"));

        } catch (Exception e) {

            log.error("Advertiser Image upload caught exception : ");
            e.printStackTrace();

            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("advimageIds/{advertisementId}")
    public ResponseEntity<List<AdvertisementImage>> getAdvertisementImages(@PathVariable int advertisementId){
        log.info("[getAdvertisementImages] advertisementId : " + advertisementId);

        List<AdvertisementImage> advertisementImages = advertisementImageServices.getAdvertisementImages(advertisementId);
        log.info("[getAdvertisementImages] number of images : " + advertisementImages.size());
        return ResponseEntity.ok().body(advertisementImages);
    }
}
