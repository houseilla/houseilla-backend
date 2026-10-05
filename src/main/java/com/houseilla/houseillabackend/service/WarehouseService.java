package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.Shop;
import com.houseilla.houseillabackend.model.Warehouse;
import com.houseilla.houseillabackend.repository.ShopRepository;
import com.houseilla.houseillabackend.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    public Warehouse saveWarehouse(Warehouse  warehouse) {
        return warehouseRepository.save(warehouse);
    }
}
