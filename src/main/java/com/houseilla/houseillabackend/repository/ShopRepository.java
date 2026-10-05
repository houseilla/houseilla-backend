package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.Land;
import com.houseilla.houseillabackend.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {
    Shop findByAdvertisementId(int advertisementId);

    @Transactional
    int deleteByAdvertisementId(int advertisementId);
}