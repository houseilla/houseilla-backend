package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.model.AdvertisementImage;
import com.houseilla.houseillabackend.model.FlatDetail;
import com.houseilla.houseillabackend.model.HouseDetail;
import com.houseilla.houseillabackend.model.LandDetail;
import com.houseilla.houseillabackend.model.OfficeDetail;
import com.houseilla.houseillabackend.model.ShopDetail;
import com.houseilla.houseillabackend.model.WarehouseDetail;
import com.houseilla.houseillabackend.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/advertisements")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping("/advertiser/{advertiserId}")
    public ResponseEntity<List<Advertisement>> getAdvertisementsByAdvertiser(@PathVariable Long advertiserId) {
        log.info("Request received to fetch advertisements for Advertiser ID: {}", advertiserId);
        List<Advertisement> listings = advertisementService.getAdvertisementsByAdvertiserId(advertiserId);
        return ResponseEntity.ok(listings);
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<Advertisement> updateAdvertisement(
            @PathVariable Long id,
            @RequestBody Advertisement advertisement) {
        log.info("Request received to update advertisement ID {}: {}", id, advertisement);
        advertisement.setId(id);
        Advertisement updatedAdvertisement = advertisementService.saveAdvertisement(advertisement);
        return ResponseEntity.ok(updatedAdvertisement);
    }

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

    @PostMapping("/land")
    public ResponseEntity<LandDetail> saveLandDetail(@RequestBody LandDetail landDetail) {
        if (landDetail.getAdvertisementId() == null || landDetail.getAdvertisementId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        LandDetail savedLand = advertisementService.saveLandDetail(landDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLand);
    }

    @PostMapping("/flat")
    public ResponseEntity<FlatDetail> saveFlatDetail(@RequestBody FlatDetail flatDetail) {
        if (flatDetail.getAdvertisementId() == null || flatDetail.getAdvertisementId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        FlatDetail savedFlat = advertisementService.saveFlatDetail(flatDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFlat);
    }

    @PostMapping("/house")
    public ResponseEntity<HouseDetail> saveHouseDetail(@RequestBody HouseDetail houseDetail) {
        if (houseDetail.getAdvertisementId() == null || houseDetail.getAdvertisementId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        HouseDetail savedHouse = advertisementService.saveHouseDetail(houseDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHouse);
    }

    @PostMapping("/office")
    public ResponseEntity<OfficeDetail> saveOfficeDetail(@RequestBody OfficeDetail officeDetail) {
        if (officeDetail.getAdvertisementId() == null || officeDetail.getAdvertisementId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        OfficeDetail savedOffice = advertisementService.saveOfficeDetail(officeDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOffice);
    }

    @PostMapping("/shop")
    public ResponseEntity<ShopDetail> saveShopDetail(@RequestBody ShopDetail shopDetail) {
        if (shopDetail.getAdvertisementId() == null || shopDetail.getAdvertisementId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        ShopDetail savedShop = advertisementService.saveShopDetail(shopDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedShop);
    }

    @PostMapping("/warehouse")
    public ResponseEntity<WarehouseDetail> saveWarehouseDetail(@RequestBody WarehouseDetail warehouseDetail) {
        if (warehouseDetail.getAdvertisementId() == null || warehouseDetail.getAdvertisementId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        WarehouseDetail savedWarehouse = advertisementService.saveWarehouseDetail(warehouseDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWarehouse);
    }

    // --- Fetch All Photos & Videos for an Advertisement ---
    @GetMapping("/{id}/images")
    public ResponseEntity<List<AdvertisementImage>> getAdvertisementImages(@PathVariable int id) {
        log.info("Fetching media items for Advertisement ID: {}", id);
        return ResponseEntity.ok(advertisementService.getAdvertisementImages(id));
    }

    // --- Upload Batch of Base64 Photos / Video for an Advertisement ---
    @PostMapping("/{id}/images")
    public ResponseEntity<List<AdvertisementImage>> uploadAdvertisementImages(
            @PathVariable int id,
            @RequestBody List<AdvertisementImage> images) {
        log.info("Uploading {} media item(s) for Advertisement ID: {}", images.size(), id);
        if (images == null || images.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<AdvertisementImage> saved = advertisementService.saveAdvertisementImages(id, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // --- Delete Single Photo / Video by File ID ---
    @DeleteMapping("/images/{fileId}")
    public ResponseEntity<Void> deleteAdvertisementImage(@PathVariable int fileId) {
        log.info("Deleting media item with File ID: {}", fileId);
        advertisementService.deleteAdvertisementImage(fileId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/images/{fileId}")
    public ResponseEntity<AdvertisementImage> updateAdvertisementImageLabel(
            @PathVariable int fileId,
            @RequestBody java.util.Map<String, String> body) {
        String newName = body.getOrDefault("name", "Property Media");
        return ResponseEntity.ok(advertisementService.updateAdvertisementImageLabel(fileId, newName));
    }

}
