package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.Enquiry;
import com.houseilla.houseillabackend.repository.EnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnquiryService {
    @Autowired
    EnquiryRepository enquiryRepository;

    public Enquiry saveEnquiry(Enquiry enquiry) {
        return enquiryRepository.save(enquiry);
    }
}
