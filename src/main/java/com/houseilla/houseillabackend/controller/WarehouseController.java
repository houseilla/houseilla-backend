package com.houseilla.houseillabackend.controller;

import com.houseilla.houseillabackend.model.Shop;
import com.houseilla.houseillabackend.model.Warehouse;
import com.houseilla.houseillabackend.service.ShopService;
import com.houseilla.houseillabackend.service.WarehouseService;
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
public class WarehouseController {


    @Autowired
    WarehouseService warehouseService;

    @PostMapping("warehouse")
    public ResponseEntity<Warehouse> saveWarehouse(@RequestBody Warehouse  warehouse) {

        log.info("Warehouse to save : {}", warehouse);
        Warehouse savedWarehouse = warehouseService.saveWarehouse(warehouse);

        if(Objects.nonNull(savedWarehouse))
            log.info("[savedWarehouse] Saved value : {} ", savedWarehouse.toString());
        else
            log.info("Unable to save warehouse : ");

        return ResponseEntity.ok(warehouse);
    }
}
