package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.model.LandDetail;
import com.houseilla.houseillabackend.service.AdvertisementService;
import com.houseilla.houseillabackend.repository.LandDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/advertisements")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private LandDetailRepository landDetailRepository;

    // --- STEP 2: Save Core Advertisement Basics ---
    @PostMapping
    public ResponseEntity<Advertisement> saveAdvertisement(@RequestBody Advertisement advertisement) {
        log.info("Request received to process advertisement wizard payload: {}", advertisement);

        Advertisement savedAdvertisement = advertisementService.saveAdvertisement(advertisement);

        if (Objects.nonNull(savedAdvertisement)) {
            log.info("Successfully committed advertisement baseline. ID: {}", savedAdvertisement.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAdvertisement);
        }

        log.error("Failed to process or persist advertisement wizard record.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // --- STEP 3: Save Decoupled Land Specifications Subtype ---
    @PostMapping("/land")
    public ResponseEntity<LandDetail> saveLandDetail(@RequestBody LandDetail landDetail) {
        log.info("Request received to save land specifications for Advertisement ID: {}", landDetail.getAdvertisementId());

        if (landDetail.getAdvertisementId() == null || landDetail.getAdvertisementId() == 0) {
            log.warn("Rejected: Cannot persist land specifications without a valid advertisementId context.");
            return ResponseEntity.badRequest().build();
        }

        // Clean up existing specifications to support safe form re-entry/edits in the wizard
        landDetailRepository.findByAdvertisementId(landDetail.getAdvertisementId())
                .ifPresent(existing -> {
                    log.info("Found pre-existing specifications record for Ad ID {}. Overwriting.", landDetail.getAdvertisementId());
                    landDetailRepository.delete(existing);
                });

        // Force a fresh database row insert
        landDetail.setId(null);
        LandDetail savedLand = landDetailRepository.save(landDetail);

        log.info("Successfully persisted land specification metrics row with ID: {}", savedLand.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLand);
    }
}
