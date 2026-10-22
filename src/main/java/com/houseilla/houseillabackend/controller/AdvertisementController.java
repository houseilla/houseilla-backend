package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/advertisements") // 🌟 Standardized base collection path
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    // Maps explicitly to: POST /api/v1/advertisements
    @PostMapping
    public ResponseEntity<Advertisement> saveAdvertisement(@RequestBody Advertisement advertisement) {
        log.info("Advertisement to save: {}", advertisement);

        // 🌟 CRITICAL: Link the bidirectional reference before saving so polymorphic mappings execute correctly
        if (advertisement.getPropertyDetail() != null) {
            advertisement.getPropertyDetail().setAdvertisement(advertisement);
        }

        Advertisement savedAdvertisement = advertisementService.saveAdvertisement(advertisement);

        if (Objects.nonNull(savedAdvertisement)) {
            log.info("Saved value: {}", savedAdvertisement.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAdvertisement);
        } else {
            log.info("Unable to save Advertisement: {}", advertisement);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
