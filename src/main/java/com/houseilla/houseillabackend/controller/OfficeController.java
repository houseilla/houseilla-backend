package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Office;
import com.houseilla.houseillabackend.model.Shop;
import com.houseilla.houseillabackend.service.OfficeService;
import com.houseilla.houseillabackend.service.ShopService;
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
public class OfficeController {


    @Autowired
    OfficeService officeService;

    @PostMapping("office")
    public ResponseEntity<Office> saveOffice(@RequestBody Office office) {

        log.info("Office to save : {}", office);
        Office savedOffice = officeService.saveOffice(office);

        if(Objects.nonNull(savedOffice))
            log.info("[savedShop] Saved value : {} ", savedOffice.toString());
        else
            log.info("Unable to save office : ");

        return ResponseEntity.ok(office);
    }
}
