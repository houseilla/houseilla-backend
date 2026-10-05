package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.House;
import com.houseilla.houseillabackend.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService {

    @Autowired
    private HouseRepository houseRepository;

    public House saveHouse(House house) {
        return houseRepository.save(house);
    }
}
