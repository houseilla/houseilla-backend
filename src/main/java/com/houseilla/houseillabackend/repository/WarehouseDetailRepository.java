package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.WarehouseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface WarehouseDetailRepository extends JpaRepository<WarehouseDetail, Long> {
    Optional<WarehouseDetail> findByAdvertisementId(Long advertisementId);

    @Transactional
    void deleteByAdvertisementId(Long advertisementId);
}
