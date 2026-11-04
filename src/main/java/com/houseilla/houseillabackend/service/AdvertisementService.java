package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.model.enums.AdvertisementStatus;
import com.houseilla.houseillabackend.repository.AdvertisementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AdvertisementService {

	@Autowired
	private AdvertisementRepository advertisementRepository;

	@Transactional
	public Advertisement saveAdvertisement(Advertisement advertisement) {
		// Path 1: Handle New Listings (Initial Wizard Step 2 Save)
		if (advertisement.getId() == null || advertisement.getId() == 0) {
			advertisement.setId(null); // Clear 0 indicator to let database generate auto-increment ID
			advertisement.setListingStatus(AdvertisementStatus.DRAFT); // Always force DRAFT initialization

			log.info("Persisting a new draft advertisement listing baseline row.");
			return advertisementRepository.save(advertisement);
		}

		// Path 2: Handle Existing Listing Updates
		return advertisementRepository.findById(advertisement.getId())
				.map(existingRecord -> {
					log.info("Updating existing advertisement record ID: {}", advertisement.getId());
					existingRecord.setPrice(advertisement.getPrice());
					existingRecord.setDescription(advertisement.getDescription());
					existingRecord.setPropertyNumber(advertisement.getPropertyNumber());
					existingRecord.setAreaName(advertisement.getAreaName());
					existingRecord.setAddressLine1(advertisement.getAddressLine1());
					existingRecord.setAddressLine2(advertisement.getAddressLine2());
					existingRecord.setDistrict(advertisement.getDistrict());
					existingRecord.setPincode(advertisement.getPincode());
					existingRecord.setLatitude(advertisement.getLatitude());
					existingRecord.setLongitude(advertisement.getLongitude());
					existingRecord.setIsPremium(advertisement.getIsPremium());
					existingRecord.setPropertyType(advertisement.getPropertyType());
					existingRecord.setPurpose(advertisement.getPurpose());

					// Retain current status or update based on wizard step changes
					if (advertisement.getListingStatus() != null) {
						existingRecord.setListingStatus(advertisement.getListingStatus());
					}

					return advertisementRepository.save(existingRecord);
				})
				.orElseThrow(() -> new IllegalArgumentException("Advertisement record with ID " + advertisement.getId() + " not found."));
	}
}
