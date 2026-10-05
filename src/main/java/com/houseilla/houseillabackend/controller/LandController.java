package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Land;
import com.houseilla.houseillabackend.service.LandService;
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
public class LandController {


    @Autowired
    LandService landService;

    @PostMapping("land")
    public ResponseEntity<Land> saveLand(@RequestBody Land land) {

        log.info("Land to save : {}", land);
        Land savedLand = landService.saveLand(land);

        if(Objects.nonNull(savedLand))
            log.info("[saveLand] Saved value : {} ", savedLand.toString());
        else
            log.info("Unable to save land : ");

        return ResponseEntity.ok(land);
    }
}
