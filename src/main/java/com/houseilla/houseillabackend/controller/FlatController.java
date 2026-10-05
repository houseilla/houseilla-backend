package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Flat;
import com.houseilla.houseillabackend.model.House;
import com.houseilla.houseillabackend.service.FlatService;
import com.houseilla.houseillabackend.service.HouseService;
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
public class FlatController {


    @Autowired
    private FlatService flatService;

    @PostMapping("flat")
    public ResponseEntity<Flat> saveHouse(@RequestBody Flat flat) {

        log.info("Flat to save : " + flat);
        Flat savedFlat = flatService.saveHouse(flat);

        if(Objects.nonNull(savedFlat))
            log.info("[savedHouse] Saved value : {} ",savedFlat.toString());
        else
            log.info("Unable to save land");

        return ResponseEntity.ok(flat);
    }
}
