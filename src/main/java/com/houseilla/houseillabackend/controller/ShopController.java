package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Shop;
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
public class ShopController {


    @Autowired
    ShopService shopService;

    @PostMapping("shop")
    public ResponseEntity<Shop> saveShop(@RequestBody Shop shop) {

        log.info("Shop to save : {}", shop);
        Shop savedShop = shopService.saveShop(shop);

        if(Objects.nonNull(savedShop))
            log.info("[savedShop] Saved value : {} ", savedShop.toString());
        else
            log.info("Unable to save shop : ");

        return ResponseEntity.ok(shop);
    }
}
