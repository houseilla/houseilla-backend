package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.Land;
import com.houseilla.houseillabackend.model.Shop;
import com.houseilla.houseillabackend.repository.LandRepository;
import com.houseilla.houseillabackend.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    @Autowired
    ShopRepository shopRepository;

    public Shop saveShop(Shop shop) {
        return shopRepository.save(shop);
    }
}
