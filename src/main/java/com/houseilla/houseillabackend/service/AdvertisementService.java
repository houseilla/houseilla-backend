package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.repository.AdvertisementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Slf4j
@Service
public class AdvertisementService {

	@Autowired
	private AdvertisementRepository advertisementRepository;

	/**
	 * Handles saving an advertisement record.
	 * Due to cascade = CascadeType.ALL, saving the parent record automatically
	 * handles the underlying creation/updates for structural images and property details.
	 */
	@Transactional
	public Advertisement saveAdvertisement(Advertisement advertisement) {
		log.info("Processing save request for advertisement listing.");

		// Automatically stamp tracking dates if it's a new post configuration
		if (advertisement.getAdvertisementId() == 0) {
			advertisement.setCreateDate(Timestamp.from(Instant.now()));
		}
		advertisement.setUpdateDate(Timestamp.from(Instant.now()));

		// Ensure the polymorphic detail child references back to this parent record cleanly
		if (Objects.nonNull(advertisement.getPropertyDetail())) {
			advertisement.getPropertyDetail().setAdvertisement(advertisement);
		}

		// Persist and return the resulting entity layout framework straight from the DB
		Advertisement savedAd = advertisementRepository.save(advertisement);
		log.info("Successfully persisted advertisement ID: {}", savedAd.getAdvertisementId());
		return savedAd;
	}
}
