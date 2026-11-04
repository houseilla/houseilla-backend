package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Advertiser;
import com.houseilla.houseillabackend.model.enums.AdvertiserContractual;
import com.houseilla.houseillabackend.model.enums.AdvertiserType;
import com.houseilla.houseillabackend.service.AdvertiserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/advertisers")
public class AdvertiserController {

    @Autowired
    private AdvertiserServices advertiserServices;

    @PostMapping
    public ResponseEntity<Advertiser> saveAdvertiser(
            @RequestBody Advertiser advertiser,
            @AuthenticationPrincipal String authenticatedEmail) {

        log.info("Secure request received to save advertiser profile for email: {}", authenticatedEmail);

        if (authenticatedEmail == null) {
            log.warn("Unauthorized profile modification XML block. Missing auth context.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Force email alignment with security token
        advertiser.setEmail(authenticatedEmail.trim().toLowerCase());

        // Process save/update operation
        log.info("Saving / Updating  advertiser profile: {}", advertiser);
        Advertiser savedAdvertiser = advertiserServices.saveAdvertiser(advertiser);

        if (Objects.nonNull(savedAdvertiser)) {
            log.info("Success! Committed profile database row details ID: {}", savedAdvertiser.getId());
            return ResponseEntity.ok().body(savedAdvertiser);
        }

        log.error("Failed to commit advertiser profile updates down to the database layers.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
