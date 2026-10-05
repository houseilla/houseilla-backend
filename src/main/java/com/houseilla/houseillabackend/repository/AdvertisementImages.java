package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.AdvertisementImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdvertisementImages extends JpaRepository<AdvertisementImage, Integer> {
    List<AdvertisementImage> findByAdvertisementId(int advertisementId);
    @Transactional
    int deleteByAdvertisementId(int advertisementId);

}
