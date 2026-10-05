package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.Land;
import com.houseilla.houseillabackend.repository.LandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandService {

    @Autowired
    LandRepository landRepository;

    public Land saveLand(Land land) {
        return landRepository.save(land);
    }
}
