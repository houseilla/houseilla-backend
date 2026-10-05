package com.houseilla.houseillabackend.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.houseilla.houseillabackend.model.AdvertisementImage;
import com.houseilla.houseillabackend.model.enums.AdvertisementPropertyType;
import com.houseilla.houseillabackend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.houseilla.houseillabackend.model.Advertisement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertisementService {

	private final AdvertisementRepository advertisementRepository;
	private final LandRepository landRepository;
	private final HouseRepository houseRepository;
	private final FlatRepository flatRepository;
	private final ShopRepository shopRepository;
	private final OfficeRepository officeRepository;
	private final WarehouseRepository warehouseRepository;
	private final AdvertisementImages advertisementImagesRepository;

	public Advertisement saveAdvertisement(Advertisement advertisement) {
		return advertisementRepository.save(advertisement);
	}

	public List<Advertisement> getAdvertisements(int pageIndex, int pageSize) {
		//Pageable limitAndSort = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createDate"));
		//return advertisementRepository.findAll(limitAndSort).getContent();
		return advertisementRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
	}

	public List<Advertisement> getAdvertisementsByAdvertiserId(int advertiserId, int pageIndex, int pageSize) {
		//Pageable limitAndSort = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createDate"));
		//return advertisementRepository.findByAdvertiserId(advertiserId, limitAndSort);

		Sort sortOrder = Sort.by(Sort.Direction.DESC, "createDate");
		return advertisementRepository.findByAdvertiserId(advertiserId, sortOrder);
	}

	public Advertisement getAdvertisementById(int advertisementId) {
		return advertisementRepository.findById(advertisementId).get();
	}

	@Transactional
	public boolean deleteAdvertisementById(Integer advertisementId) {
		log.info("Initiating secure deletion sequence for Advertisement ID: {}", advertisementId);

		Optional<Advertisement> advertisementOpt = advertisementRepository.findById(advertisementId);
		if (advertisementOpt.isEmpty()) {
			log.warn("Aborting deletion: Advertisement ID {} does not exist in the database.", advertisementId);
			return false;
		}

		Advertisement advertisement = advertisementOpt.get();

		int typeIndex = advertisement.getPropertyType();
		AdvertisementPropertyType propertyType = null;

		if (typeIndex >= 0 && typeIndex < AdvertisementPropertyType.values().length) {

			propertyType = AdvertisementPropertyType.values()[typeIndex];

			int childDeletedCount = 0;

			switch (propertyType) {
				case PRIVATE_LAND:
				case COMMERCIAL_LAND:
					log.info("Cleaning up associated Land entry references...");
					childDeletedCount = landRepository.deleteByAdvertisementId(advertisementId);
					break;

				case HOUSE:
					log.info("Cleaning up associated House entry references...");
					childDeletedCount = houseRepository.deleteByAdvertisementId(advertisementId);
					break;

				case FLAT:
					log.info("Cleaning up associated Flat entry references...");
					childDeletedCount = flatRepository.deleteByAdvertisementId(advertisementId);
					break;

				case SHOP:
					log.info("Cleaning up associated Shop entry references...");
					childDeletedCount = shopRepository.deleteByAdvertisementId(advertisementId);
					break;

				case OFFICE:
					log.info("Cleaning up associated Office entry references...");
					childDeletedCount = officeRepository.deleteByAdvertisementId(advertisementId);
					break;

				case WAREHOUSE:
					log.info("Cleaning up associated Warehouse entry references...");
					childDeletedCount = warehouseRepository.deleteByAdvertisementId(advertisementId);
					break;

				default:
					log.warn("Unrecognized property type condition encountered: {}", propertyType);
					break;
			}

			log.debug("Sub-property subtype deletion check finished. Rows affected: {}", childDeletedCount);

			int imagesDeleted = advertisementImagesRepository.deleteByAdvertisementId(advertisementId);
			log.info("Successfully dropped {} media attachment resource record(s).", imagesDeleted);
		}

		advertisementRepository.delete(advertisement);
		log.info("Core parent Advertisement record successfully purged from the database.");

		return true;
	}
}
