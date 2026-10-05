package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Integer> {
    Flat findByAdvertisementId(int advertisementId);

    @Transactional
    int deleteByAdvertisementId(int advertisementId);
}