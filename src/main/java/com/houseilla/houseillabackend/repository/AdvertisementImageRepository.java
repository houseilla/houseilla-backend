package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.AdvertisementImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdvertisementImageRepository extends JpaRepository<AdvertisementImage, Integer> {
    List<AdvertisementImage> findByAdvertisementId(int advertisementId);

    @Transactional
    void deleteByAdvertisementId(int advertisementId);
}
