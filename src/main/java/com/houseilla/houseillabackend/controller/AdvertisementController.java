package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.model.FlatDetail;
import com.houseilla.houseillabackend.model.LandDetail;
import com.houseilla.houseillabackend.service.AdvertisementService;
import com.houseilla.houseillabackend.repository.LandDetailRepository;
import com.houseilla.houseillabackend.repository.FlatDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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

    @Autowired
    private FlatDetailRepository flatDetailRepository;

    // --- Fetch All Advertisements by Advertiser ID ---
    @GetMapping("/advertiser/{advertiserId}")
    public ResponseEntity<List<Advertisement>> getAdvertisementsByAdvertiser(@PathVariable Long advertiserId) {
        log.info("Request received to fetch advertisements for Advertiser ID: {}", advertiserId);
        List<Advertisement> listings = advertisementService.getAdvertisementsByAdvertiserId(advertiserId);
        return ResponseEntity.ok(listings);
    }

    // --- Create Core Advertisement Basics ---
    @PostMapping
    public ResponseEntity<Advertisement> saveAdvertisement(@RequestBody Advertisement advertisement) {
        log.info("Request received to process new advertisement wizard payload: {}", advertisement);

        Advertisement savedAdvertisement = advertisementService.saveAdvertisement(advertisement);

        if (Objects.nonNull(savedAdvertisement)) {
            log.info("Successfully committed advertisement baseline. ID: {}", savedAdvertisement.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAdvertisement);
        }

        log.error("Failed to process or persist advertisement wizard record.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // --- Update Existing Core Advertisement Basics ---
    @PutMapping("/{id}")
    public ResponseEntity<Advertisement> updateAdvertisement(
            @PathVariable Long id,
            @RequestBody Advertisement advertisement) {
        log.info("Request received to update advertisement ID {}: {}", id, advertisement);
        advertisement.setId(id);

        Advertisement updatedAdvertisement = advertisementService.saveAdvertisement(advertisement);
        return ResponseEntity.ok(updatedAdvertisement);
    }

    // --- Delete Advertisement & Associated Property Details ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Long id) {
        log.info("Request received to delete advertisement ID: {}", id);
        try {
            advertisementService.deleteAdvertisement(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.warn("Delete failed: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // --- Save Decoupled Land Specifications Subtype ---
    @PostMapping("/land")
    public ResponseEntity<LandDetail> saveLandDetail(@RequestBody LandDetail landDetail) {
        log.info("Request received to save land specifications for Advertisement ID: {}", landDetail.getAdvertisementId());

        if (landDetail.getAdvertisementId() == null || landDetail.getAdvertisementId() == 0) {
            log.warn("Rejected: Cannot persist land specifications without a valid advertisementId context.");
            return ResponseEntity.badRequest().build();
        }

        landDetailRepository.findByAdvertisementId(landDetail.getAdvertisementId())
                .ifPresent(existing -> {
                    log.info("Found pre-existing specifications record for Ad ID {}. Overwriting.", landDetail.getAdvertisementId());
                    landDetailRepository.delete(existing);
                });

        landDetail.setId(null);
        LandDetail savedLand = landDetailRepository.save(landDetail);

        log.info("Successfully persisted land specification metrics row with ID: {}", savedLand.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLand);
    }

    // --- Save Decoupled Flat Specifications Subtype ---
    @PostMapping("/flat")
    public ResponseEntity<FlatDetail> saveFlatDetail(@RequestBody FlatDetail flatDetail) {
        log.info("Request received to save flat specifications for Advertisement ID: {}", flatDetail.getAdvertisementId());

        if (flatDetail.getAdvertisementId() == null || flatDetail.getAdvertisementId() == 0) {
            log.warn("Rejected: Cannot persist flat specifications without a valid advertisementId context.");
            return ResponseEntity.badRequest().build();
        }

        flatDetailRepository.findByAdvertisementId(flatDetail.getAdvertisementId())
                .ifPresent(existing -> {
                    log.info("Found pre-existing flat specifications record for Ad ID {}. Overwriting.", flatDetail.getAdvertisementId());
                    flatDetailRepository.delete(existing);
                });

        flatDetail.setId(null);
        FlatDetail savedFlat = flatDetailRepository.save(flatDetail);

        log.info("Successfully persisted flat specification metrics row with ID: {}", savedFlat.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFlat);
    }
}
