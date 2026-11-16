package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.model.enums.AdvertisementStatus;
import com.houseilla.houseillabackend.repository.AdvertisementRepository;
import com.houseilla.houseillabackend.repository.LandDetailRepository;
import com.houseilla.houseillabackend.repository.FlatDetailRepository;
import com.houseilla.houseillabackend.repository.HouseDetailRepository;
import com.houseilla.houseillabackend.repository.OfficeDetailRepository;
import com.houseilla.houseillabackend.repository.ShopDetailRepository;
import com.houseilla.houseillabackend.repository.WarehouseDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
public class AdvertisementService {

	@Autowired
	private AdvertisementRepository advertisementRepository;

	@Autowired
	private LandDetailRepository landDetailRepository;

	@Autowired
	private FlatDetailRepository flatDetailRepository;

	@Autowired
	private HouseDetailRepository houseDetailRepository;

	@Autowired
	private OfficeDetailRepository officeDetailRepository;

	@Autowired
	private ShopDetailRepository shopDetailRepository;

	@Autowired
	private WarehouseDetailRepository warehouseDetailRepository;


	public List<Advertisement> getAdvertisementsByAdvertiserId(Long advertiserId) {
		log.info("Fetching all advertisements for advertiser ID: {}", advertiserId);
		return advertisementRepository.findByAdvertiserId(advertiserId);
	}

	@Transactional
	public Advertisement saveAdvertisement(Advertisement advertisement) {
		if (advertisement.getId() == null || advertisement.getId() == 0) {
			advertisement.setId(null);
			advertisement.setListingStatus(AdvertisementStatus.DRAFT);

			log.info("Persisting a new draft advertisement listing baseline row.");
			return advertisementRepository.save(advertisement);
		}

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

					if (advertisement.getListingStatus() != null) {
						existingRecord.setListingStatus(advertisement.getListingStatus());
					}

					return advertisementRepository.save(existingRecord);
				})
				.orElseThrow(() -> new IllegalArgumentException("Advertisement record with ID " + advertisement.getId() + " not found."));
	}

	@Transactional
	public void deleteAdvertisement(Long id) {
		Advertisement existing = advertisementRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Advertisement record with ID " + id + " not found."));

		log.info("Deleting property details and advertisement record for ID: {} (Type: {})", id, existing.getPropertyType());

		if (existing.getPropertyType() != null) {
			switch (existing.getPropertyType()) {
				case PRIVATE_LAND:
				case COMMERCIAL_LAND:
					landDetailRepository.deleteByAdvertisementId(id);
					break;
				case FLAT:
					flatDetailRepository.deleteByAdvertisementId(id);
					break;
				case HOUSE:
					houseDetailRepository.deleteByAdvertisementId(id);
					break;
				case OFFICE:
					officeDetailRepository.deleteByAdvertisementId(id);
					break;
				case SHOP:
					shopDetailRepository.deleteByAdvertisementId(id);
					break;
				case WAREHOUSE:
					warehouseDetailRepository.deleteByAdvertisementId(id);
					break;
			}
		}

		advertisementRepository.delete(existing);
		log.info("Successfully deleted advertisement ID: {}", id);
	}
}
