package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.Office;
import com.houseilla.houseillabackend.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfficeService {

    @Autowired
    OfficeRepository officeRepository;

    public Office saveOffice(Office office) {
        return officeRepository.save(office);
    }
}
