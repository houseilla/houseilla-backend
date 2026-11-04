package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.Advertiser;
import com.houseilla.houseillabackend.repository.AdvertiserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AdvertiserServices {

	@Autowired
	private AdvertiserRepository advertiserRepository;

	public Advertiser getAdvertiserByEmail(String email) {
		return advertiserRepository.findByEmail(email);
	}

	@Transactional
	public Advertiser saveAdvertiser(Advertiser advertiser) {
		// Path 1: Handle New Registrations (ID is missing or explicitly sent as 0)
		if (advertiser.getId() == null || advertiser.getId() == 0) {
			advertiser.setId(null); // Strips the 0 so Hibernate triggers a clean auto-increment INSERT
			return advertiserRepository.save(advertiser);
		}

		// Path 2: Handle Secure Profile Updates
		return advertiserRepository.findById(advertiser.getId())
				.map(existingRecord -> {
					existingRecord.setName(advertiser.getName());
					existingRecord.setPhone(advertiser.getPhone());
					existingRecord.setLocation(advertiser.getLocation());
					existingRecord.setAddress(advertiser.getAddress());
					existingRecord.setAdvertiserType(advertiser.getAdvertiserType());
					existingRecord.setContractualStatus(advertiser.getContractualStatus());
					return advertiserRepository.save(existingRecord);
				})
				.orElseThrow(() -> new IllegalArgumentException("Target advertiser profile ID " + advertiser.getId() + " does not exist."));
	}
}
