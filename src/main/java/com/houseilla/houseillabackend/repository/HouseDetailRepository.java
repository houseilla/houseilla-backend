package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.HouseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface HouseDetailRepository extends JpaRepository<HouseDetail, Long> {
    Optional<HouseDetail> findByAdvertisementId(Long advertisementId);

    @Transactional
    void deleteByAdvertisementId(Long advertisementId);
}
