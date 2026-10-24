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
		// If it's a brand new profile registration, insert it directly
		if (advertiser.getAdvertiserId() == 0) {
			return advertiserRepository.save(advertiser);
		}

		// If it's an update, fetch the record first to keep lazy-loading collections intact
		return advertiserRepository.findById(advertiser.getAdvertiserId())
				.map(existingRecord -> {
					// Manually map only the text profile fields changed in Wizard Step 2
					existingRecord.setName(advertiser.getName());
					//existingRecord.setEmail(advertiser.getEmail());
					existingRecord.setPhone(advertiser.getPhone());
					//existingRecord.setLocation(advertiser.getLocation());
					existingRecord.setType(advertiser.getType());
					existingRecord.setContractual(advertiser.getContractual());
					//existingRecord.setAddress(advertiser.getAddress());

					// This saves only the core fields and ignores the heavy advertisements collection
					return advertiserRepository.save(existingRecord);
				})
				.orElseGet(() -> advertiserRepository.save(advertiser));
	}

}
