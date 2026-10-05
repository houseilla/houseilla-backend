package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.model.Enquiry;
import com.houseilla.houseillabackend.service.AdvertisementService;
import com.houseilla.houseillabackend.service.EnquiryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class EnquiryController {
    @Autowired
    EnquiryService enquiryService;

    @PostMapping("enquiry")
    public ResponseEntity<Enquiry> saveEnquiry(@RequestBody Enquiry enquiry) {

        log.info("Enquiry to save : {}" , enquiry);
        Enquiry savedEnquiry = enquiryService.saveEnquiry(enquiry);

        if(Objects.nonNull(savedEnquiry))
            log.info("[savedEnquiry] Saved value : {} ", savedEnquiry.toString());
        else
            log.info("Unable to save Enquiry : {}", savedEnquiry.toString());

        return ResponseEntity.ok().body(savedEnquiry);
    }
}
