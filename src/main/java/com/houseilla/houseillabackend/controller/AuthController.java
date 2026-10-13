package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Advertiser;
import com.houseilla.houseillabackend.model.OtpRequest;
import com.houseilla.houseillabackend.security.JwtUtil;
import com.houseilla.houseillabackend.service.AdvertiserServices;
import com.houseilla.houseillabackend.service.OtpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})

public class AuthController {

    @Autowired
    private OtpService otpService; // Polmorphic implementation handled dynamically by active Spring profile

    @Autowired
    private AdvertiserServices advertiserServices;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, Object>> sendOtp(@RequestBody OtpRequest otpRequest) {

        log.info("Sent OTP Request to  : {}" , otpRequest.toString());
        Map<String, Object> jsonResponse = new HashMap<>();

        String cleanPhone = otpRequest.getPhone() != null ? otpRequest.getPhone().trim() : "";
        String cleanEmail = otpRequest.getEmail() != null ? otpRequest.getEmail().trim().toLowerCase() : "";

        if (cleanPhone.isEmpty() || cleanEmail.isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Phone and Email details are required parameters.");
            return ResponseEntity.badRequest().body(jsonResponse);
        }

        try {
            // Process dynamically across selected service target logic profile bounds
            otpService.processOtpRequest(cleanEmail, cleanPhone);

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Verification code generated successfully.");
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception exception) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error triggering verification generation pipeline: " + exception.getMessage());
            return ResponseEntity.internalServerError().body(jsonResponse);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody OtpRequest otpRequest) {

        log.info("Verify OTP Request to  : {}" , otpRequest.toString());
        Map<String, Object> jsonResponse = new HashMap<>();

        String cleanEmail = otpRequest.getEmail() != null ? otpRequest.getEmail().trim().toLowerCase() : "";
        String cleanPhone = otpRequest.getPhone() != null ? otpRequest.getPhone().trim() : "";
        String submittedCode = otpRequest.getOtpCode() != null ? otpRequest.getOtpCode().trim() : "";

        if (submittedCode.isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Verification code field cannot be missing or blank.");
            return ResponseEntity.badRequest().body(jsonResponse);
        }

        // Call active configuration validator execution logic block
        boolean isValid = otpService.validateOtpCode(cleanEmail, submittedCode);

        if (isValid) {
            jsonResponse.put("success", true);
            jsonResponse.put("message", "Advertiser successfully identified.");
            String realToken = jwtUtil.generateToken(cleanEmail, cleanPhone);
            log.info("Generated JWT Token : {}", realToken);
            jsonResponse.put("jwtToken", realToken);

            Advertiser existingAdvertiser = advertiserServices.getAdvertiserByPhoneNo(cleanPhone);
            if (existingAdvertiser != null) {
                log.info("Advertiser already exists for phone : {} ", cleanPhone);
                jsonResponse.put("isPreExisting", true);
                jsonResponse.put("advertiser", existingAdvertiser);
            } else {
                log.info("Advertiser does not exist for phone : {} ", cleanPhone);
                jsonResponse.put("isPreExisting", false);
                jsonResponse.put("advertiser", null);
            }

            return ResponseEntity.ok(jsonResponse);
        }

        jsonResponse.put("success", false);
        jsonResponse.put("message", "The verification code entered is incorrect or has expired.");
        return ResponseEntity.status(401).body(jsonResponse);
    }
}
