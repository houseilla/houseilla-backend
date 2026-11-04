package com.houseilla.houseillabackend.repository;

import com.houseilla.houseillabackend.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    // Fetches all listings matching a specific advertiser index
    List<Advertisement> findByAdvertiserId(Long advertiserId);
}
