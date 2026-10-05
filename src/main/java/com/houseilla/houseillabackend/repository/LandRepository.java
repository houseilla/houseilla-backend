package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LandRepository extends JpaRepository<Land, Integer> {
    Land findByAdvertisementId(int advertisementId);

    @Transactional
    int deleteByAdvertisementId(int advertisementId);
}