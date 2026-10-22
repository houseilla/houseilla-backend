package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Advertiser;
import com.houseilla.houseillabackend.model.OtpRequest;
import com.houseilla.houseillabackend.security.JwtUtil;
import com.houseilla.houseillabackend.service.AdvertiserServices;
import com.houseilla.houseillabackend.service.OtpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth") // 🌟 Standardized versioned route path mapping
public class AuthController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private AdvertiserServices advertiserServices;

    @Autowired
    private JwtUtil jwtUtil;

    // Maps explicitly to: POST /api/v1/auth/send-otp
    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, Object>> sendOtp(@RequestBody OtpRequest otpRequest) {
        log.info("Sent OTP Request to: {}", otpRequest.toString());
        Map<String, Object> jsonResponse = new HashMap<>();

        String cleanPhone = otpRequest.getPhone() != null ? otpRequest.getPhone().trim() : "";
        String cleanEmail = otpRequest.getEmail() != null ? otpRequest.getEmail().trim().toLowerCase() : "";

        if (cleanPhone.isEmpty() || cleanEmail.isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Phone and Email details are required parameters.");
            return ResponseEntity.badRequest().body(jsonResponse);
        }

        try {
            otpService.processOtpRequest(cleanEmail, cleanPhone);
            jsonResponse.put("success", true);
            return ResponseEntity.ok().body(jsonResponse);
        } catch (Exception e) {
            log.error("Failed to process OTP request", e);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Internal server error processing OTP.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse);
        }
    }

    // Maps explicitly to: POST /api/v1/auth/verify-otp
    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody OtpRequest otpRequest) {
        log.info("Verify OTP Request to: {}", otpRequest.toString());
        Map<String, Object> jsonResponse = new HashMap<>();

        String cleanEmail = otpRequest.getEmail() != null ? otpRequest.getEmail().trim().toLowerCase() : "";
        String cleanPhone = otpRequest.getPhone() != null ? otpRequest.getPhone().trim() : "";
        String submittedCode = otpRequest.getOtpCode() != null ? otpRequest.getOtpCode().trim() : "";

        if (submittedCode.isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Verification code field cannot be missing or blank.");
            return ResponseEntity.badRequest().body(jsonResponse);
        }

        boolean isValid = otpService.validateOtpCode(cleanEmail, submittedCode);

        if (isValid) {
            jsonResponse.put("success", true);
            jsonResponse.put("message", "Advertiser successfully identified.");

            String realToken = jwtUtil.generateToken(cleanEmail, cleanPhone);
            log.info("Generated JWT Token: {}", realToken);
            jsonResponse.put("jwtToken", realToken);

            // 🌟 FIX: Lookup profile directly via the verified email string variable context
            Advertiser existingAdvertiser = advertiserServices.getAdvertiserByEmail(cleanEmail);

            if (existingAdvertiser != null) {
                log.info("Advertiser already exists for email: {} , advertiser : {} ", cleanEmail, existingAdvertiser);
                jsonResponse.put("isProfileExisting", true);
                jsonResponse.put("advertiser", existingAdvertiser);
            } else {
                log.info("Advertiser does not exist for email: {}", cleanEmail);
                jsonResponse.put("isProfileExisting", false);
                jsonResponse.put("advertiser", null);
            }

            return ResponseEntity.ok(jsonResponse);
        }

        jsonResponse.put("success", false);
        jsonResponse.put("message", "The verification code entered is incorrect or has expired.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonResponse);
    }
}
