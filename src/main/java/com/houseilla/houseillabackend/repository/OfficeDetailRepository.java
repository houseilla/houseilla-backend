package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.OfficeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OfficeDetailRepository extends JpaRepository<OfficeDetail, Long> {
    Optional<OfficeDetail> findByAdvertisementId(Long advertisementId);

    @Transactional
    void deleteByAdvertisementId(Long advertisementId);
}
