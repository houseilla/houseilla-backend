package com.houseilla.houseillabackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.houseilla.houseillabackend.model.Advertiser;

import java.util.List;

@Repository
public interface AdvertiserRepository extends JpaRepository<Advertiser, Integer> {
	Advertiser findByPhone(String phone);
	Advertiser findByAdvertiserId(int advertiserId);
}
