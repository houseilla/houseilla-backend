package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.LandDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LandDetailRepository extends JpaRepository<LandDetail, Long> {
    // Finds specific plot metrics linked to a listing core
    Optional<LandDetail> findByAdvertisementId(Long advertisementId);

    // Explicit cascade helper handling programmatic deletion blocks
    @Transactional
    void deleteByAdvertisementId(Long advertisementId);
}
