package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.exception.ResourceNotFoundException;
import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.model.Advertiser;
import com.houseilla.houseillabackend.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class AdvertisementController {

    @Autowired
    AdvertisementService advertisementService;

    @PostMapping("advertisement")
    public ResponseEntity<Advertisement> saveAdvertisement(@RequestBody Advertisement advertisement) {

        log.info("Advertisement to save : {}" ,advertisement);
        Advertisement savedAdvertisement = advertisementService.saveAdvertisement(advertisement);

        if(Objects.nonNull(savedAdvertisement))
            log.info("[saveAdvertisement] Saved value : {} ", savedAdvertisement.toString());
        else
            log.info("Unable to save Advertisement : {}", savedAdvertisement.toString());

        return ResponseEntity.ok().body(savedAdvertisement);
    }

    @GetMapping("advertisements/{pageIndex}")
    public ResponseEntity<List<Advertisement>> getAllAdvertisements(@PathVariable int pageIndex){
        log.info("[getAllAdvertisements] pageIndex : {}", pageIndex);

        List<Advertisement> advertisements = new ArrayList<>();
        advertisements = advertisementService.getAdvertisements(pageIndex, 10);

        log.info("Get advertisements size : {}", advertisements.size());

        return ResponseEntity.ok().body(advertisements);
    }

    @Transactional(readOnly = true)
    @GetMapping("advertiser/advertisements")
    public ResponseEntity<List<Advertisement>> getAdvertisementsByAdvertiserId(
            @RequestParam("advertiserId") int advertiserId,
            @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex) {

        log.info("Fetching advertisements for advertiserId: {}, pageIndex: {}", advertiserId, pageIndex);

        List<Advertisement> advertisements = advertisementService.getAdvertisementsByAdvertiserId(advertiserId, pageIndex, 10);

        return ResponseEntity.ok().body(advertisements);
    }


    @DeleteMapping("/advertisement")
    public ResponseEntity<Map<String, Boolean>> deleteAdvertisement(
            @RequestParam("advertisementId") Integer advertisementId) {

        log.info("REST delete request captured via RequestParam for ID: {}", advertisementId);

        // 1. Fail Fast: Validate input parameters immediately
        if (Objects.isNull(advertisementId) || advertisementId <= 0) {
            log.warn("Aborting delete request: Invalid advertisementId provided: {}", advertisementId);
            return ResponseEntity.badRequest().build(); // Cleaner shorthand for badRequest().body(null)
        }

        // 2. Execute safe transactional deletion sequence
        boolean isDeleted = advertisementService.deleteAdvertisementById(advertisementId);

        // 3. Build accurate status response structure
        Map<String, Boolean> response = new HashMap<>();

        if (isDeleted) {
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response); // Return 200 OK with {"deleted": true}
        } else {
            log.warn("Delete execution finished: Advertisement ID {} not found.", advertisementId);
            response.put("deleted", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Return 404 Not Found with {"deleted": false}
        }
    }



/*
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        // Fetch the entity containing your image data (stored as @Lob or byte[])
        ImageEntity imageEntity = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));

        byte[] imageData = imageEntity.getData();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Or dynamic based on entity.getType()
                .body(imageData);
    }
*/
}
