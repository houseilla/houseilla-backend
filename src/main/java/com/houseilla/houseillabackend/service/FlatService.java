package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.Flat;
import com.houseilla.houseillabackend.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlatService {

    @Autowired
    private FlatRepository flatRepository;

    public Flat saveHouse(Flat flat) {
        return flatRepository.save(flat);
    }
}
