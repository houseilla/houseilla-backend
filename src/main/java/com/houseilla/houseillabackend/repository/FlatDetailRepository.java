package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.FlatDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FlatDetailRepository extends JpaRepository<FlatDetail, Long> {
    Optional<FlatDetail> findByAdvertisementId(Long advertisementId);

    @Transactional
    void deleteByAdvertisementId(Long advertisementId);
}
