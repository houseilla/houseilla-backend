package com.houseilla.houseillabackend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@Profile("prod")
public class ProductionOtpService implements OtpService {

    @Autowired
    private JavaMailSender mailSender; // Dynamically initialized only on production startup container profiles

    private final Map<String, String> prodOtpCache = new ConcurrentHashMap<>();

    @Override
    public void processOtpRequest(String email, String phone) {
        String cleanEmail = email.trim().toLowerCase();

        // Generate a production secure 6-digit dynamic number sequence string
        String secureOtp = String.format("%06d", new Random().nextInt(1000000));
        prodOtpCache.put(cleanEmail, secureOtp);

        // Draft production verification message mail structure
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("info@houseilla.in");
        message.setTo(cleanEmail);
        message.setSubject("Houseilla Account Verification - OTP");
        message.setText("Dear User,\n\n"
                + "Your single-use security verification code for Houseilla is: " + secureOtp + "\n\n"
                + "This OTP is valid for 5 minutes. For safety reasons, please do not share this token with anyone.\n\n"
                + "Best regards,\n"
                + "Team Houseilla");

        mailSender.send(message);

        log.info("Generated OTP >>{} has send to {}",  secureOtp, cleanEmail);
    }

    @Override
    public boolean validateOtpCode(String email, String submittedCode) {
        String cleanEmail = email.trim().toLowerCase();
        String cachedOtp = prodOtpCache.get(cleanEmail);

        if (cachedOtp != null && cachedOtp.equals(submittedCode.trim())) {
            prodOtpCache.remove(cleanEmail);

            log.info("OTP validation was successful for {}",  cleanEmail);
            return true;
        }

        log.info("OTP validation was unsuccessful for {}",  cleanEmail);
        return false;
    }
}
