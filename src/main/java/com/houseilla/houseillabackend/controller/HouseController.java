package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.House;
import com.houseilla.houseillabackend.model.Land;
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
public class HouseController {


    @Autowired
    private HouseService houseService;

    @PostMapping("house")
    public ResponseEntity<House> saveHouse(@RequestBody House house) {

        log.info("House to save : " + house);
        House savedHouse = houseService.saveHouse(house);

        if(Objects.nonNull(savedHouse))
            log.info("[savedHouse] Saved value : {}", savedHouse.toString());
        else
            log.info("Unable to save land : {} ", savedHouse.toString());

        return ResponseEntity.ok(house);
    }
}
