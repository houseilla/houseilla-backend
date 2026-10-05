package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.Advertiser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.houseilla.houseillabackend.model.Advertisement;

import java.util.List;
import java.util.Set;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {
    List<Advertisement> findByAdvertiserId(Integer advertiserId, Pageable pageable);
    List<Advertisement> findByAdvertiserId(Integer advertiserId, Sort sort);

    int deleteByAdvertisementId(int advertisementId);
}
