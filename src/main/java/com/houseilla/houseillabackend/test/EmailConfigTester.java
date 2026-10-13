package com.houseilla.houseillabackend.test;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

public class EmailConfigTester {

    public static void main(String[] args) {
        System.out.println("=====================================================================");
        System.out.println("STARTING LOCAL HOUSEILLA MAILTRAP SANDBOX INTEGRATION CHECK");
        System.out.println("=====================================================================");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // 1. Map target configuration settings directly to Mailtrap unblocked port
        mailSender.setHost("sandbox.smtp.mailtrap.io");
        mailSender.setPort(2525);
        mailSender.setUsername("1e147b86b120a2");
        mailSender.setPassword("4235a15957898c"); // <-- Paste your revealed password here

        // 2. Setup standard connection handshakes
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "true"); // Keeps total traffic dialogue tracking visible

        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.writetimeout", "5000");

        // 3. Draft sandbox validation message
        SimpleMailMessage validationMessage = new SimpleMailMessage();
        validationMessage.setFrom("info@houseilla.in");
        validationMessage.setTo("abhishek.purakayastha@gmail.com");
        validationMessage.setSubject("HOUSEILLA LOCAL DEV ENVIRONMENT DIAGNOSTIC - SUCCESS");
        validationMessage.setText("Dear Developer,\n\n"
                + "Awesome! The Mailtrap pipeline is completely operational.\n\n"
                + "You can view this message inside your browser dashboard at mailtrap.io.\n\n"
                + "Time Log: " + java.time.LocalDateTime.now());

        // 4. Send sequence execution loop
        try {
            System.out.println("Initializing clean Mailtrap socket handshake sequence...");
            mailSender.send(validationMessage);
            System.out.println("\n[SUCCESS] Mailtrap configuration check completely validated!");
        } catch (Exception exception) {
            System.err.println("\n[FAILURE] Mailtrap handshakes crashed!");
            exception.printStackTrace();
        }

        System.out.println("=====================================================================");
    }
}