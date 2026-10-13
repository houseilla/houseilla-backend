package com.houseilla.houseillabackend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@Profile("dev")
public class LocalOtpService implements OtpService {

    // Simple temporary runtime store linking user email with their generated verification value
    private final Map<String, String> devOtpCache = new ConcurrentHashMap<>();

    @Override
    public void processOtpRequest(String email, String phone) {
        String cleanEmail = email.trim().toLowerCase();

        // Generate a fast 6-digit numeric verification token
        String generatedOtp = String.format("%06d", new Random().nextInt(1000000));

        // Cache code matching key reference
        devOtpCache.put(cleanEmail, generatedOtp);

        log.info("Generated OTP >>{} has send to {}",  generatedOtp, cleanEmail);
    }

    @Override
    public boolean validateOtpCode(String email, String submittedCode) {
        String cleanEmail = email.trim().toLowerCase();
        String cachedOtp = devOtpCache.get(cleanEmail);

        if (cachedOtp != null && cachedOtp.equals(submittedCode.trim())) {
            devOtpCache.remove(cleanEmail); // Evict token immediately upon success

            log.info("OTP validation was successful for {}",  cleanEmail);
            return true;
        }

        log.info("OTP validation was unsuccessful for {}",  cleanEmail);
        return false;
    }
}
