package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Advertiser;
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

    // Maps explicitly to: POST /api/v1/advertisers
    @PostMapping
    public ResponseEntity<Advertiser> saveAdvertiser(
            @RequestBody Advertiser advertiser,
            @AuthenticationPrincipal String authenticatedEmail) {

        log.info("Secure request received to save advertiser profile for email: {}", authenticatedEmail);

        // A. Security Check: Terminate request instantly if Spring context fails to resolve a valid principal token
        if (authenticatedEmail == null) {
            log.warn("Unauthorized profile modification attempt blocked. Missing authentication credentials context.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // B. Data Harmonization: Lock the model's email string field to the verified token value
        advertiser.setEmail(authenticatedEmail.trim().toLowerCase());

        // C. Database Action: Commit the updated entity structure securely via your service mapping rules
        Advertiser savedAdvertiser = advertiserServices.saveAdvertiser(advertiser);

        if (Objects.nonNull(savedAdvertiser)) {
            log.info("Success! Committed profile database row details: {}", savedAdvertiser.toString());
            return ResponseEntity.ok().body(savedAdvertiser);
        }

        log.error("Failed to commit advertiser profile updates down to the database layers.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
