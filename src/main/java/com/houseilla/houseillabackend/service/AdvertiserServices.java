package com.houseilla.houseillabackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.houseilla.houseillabackend.model.Advertiser;
import com.houseilla.houseillabackend.repository.AdvertiserRepository;

@Service
public class AdvertiserServices {
	
	@Autowired 
	AdvertiserRepository advertiserRepository;

	public AdvertiserServices() {}

	public Advertiser getAdvertiserByEmail(String email) {
		// 🌟 Calls your newly defined repository query method directly
		return advertiserRepository.findByEmail(email);
	}

	public Advertiser saveAdvertiser(Advertiser advertiser) {
		return advertiserRepository.save(advertiser);
	}
}
