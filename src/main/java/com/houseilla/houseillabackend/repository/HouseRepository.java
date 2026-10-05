package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface HouseRepository extends JpaRepository<House, Integer> {
    House findByAdvertisementId(int advertisementId);

    @Transactional
    int deleteByAdvertisementId(int advertisementId);
}