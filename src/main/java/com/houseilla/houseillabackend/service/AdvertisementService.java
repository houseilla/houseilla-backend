package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.model.AdvertisementImage;
import com.houseilla.houseillabackend.model.FlatDetail;
import com.houseilla.houseillabackend.model.HouseDetail;
import com.houseilla.houseillabackend.model.LandDetail;
import com.houseilla.houseillabackend.model.OfficeDetail;
import com.houseilla.houseillabackend.model.ShopDetail;
import com.houseilla.houseillabackend.model.WarehouseDetail;
import com.houseilla.houseillabackend.model.enums.AdvertisementStatus;
import com.houseilla.houseillabackend.repository.AdvertisementImageRepository;
import com.houseilla.houseillabackend.repository.AdvertisementRepository;
import com.houseilla.houseillabackend.repository.FlatDetailRepository;
import com.houseilla.houseillabackend.repository.HouseDetailRepository;
import com.houseilla.houseillabackend.repository.LandDetailRepository;
import com.houseilla.houseillabackend.repository.OfficeDetailRepository;
import com.houseilla.houseillabackend.repository.ShopDetailRepository;
import com.houseilla.houseillabackend.repository.WarehouseDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final LandDetailRepository landDetailRepository;
    private final FlatDetailRepository flatDetailRepository;
    private final HouseDetailRepository houseDetailRepository;
    private final OfficeDetailRepository officeDetailRepository;
    private final ShopDetailRepository shopDetailRepository;
    private final WarehouseDetailRepository warehouseDetailRepository;
    private final AdvertisementImageRepository advertisementImageRepository;

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
                .orElseThrow(() -> new IllegalArgumentException(
                        "Advertisement record with ID " + advertisement.getId() + " not found."));
    }

    @Transactional
    public void deleteAdvertisement(Long id) {
        Advertisement existing = advertisementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Advertisement record with ID " + id + " not found."));

        log.info("Deleting media, property details, and advertisement record for ID: {} (Type: {})", id,
                existing.getPropertyType());

        advertisementImageRepository.deleteByAdvertisementId(id.intValue());

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

    @Transactional
    public LandDetail saveLandDetail(LandDetail landDetail) {
        landDetailRepository.findByAdvertisementId(landDetail.getAdvertisementId())
                .ifPresent(existing -> {
                    landDetailRepository.delete(existing);
                    landDetailRepository.flush();
                });

        landDetail.setId(null);
        return landDetailRepository.save(landDetail);
    }

    @Transactional
    public FlatDetail saveFlatDetail(FlatDetail flatDetail) {
        flatDetailRepository.findByAdvertisementId(flatDetail.getAdvertisementId())
                .ifPresent(existing -> {
                    flatDetailRepository.delete(existing);
                    flatDetailRepository.flush();
                });

        flatDetail.setId(null);
        return flatDetailRepository.save(flatDetail);
    }

    @Transactional
    public HouseDetail saveHouseDetail(HouseDetail houseDetail) {
        houseDetailRepository.findByAdvertisementId(houseDetail.getAdvertisementId())
                .ifPresent(existing -> {
                    houseDetailRepository.delete(existing);
                    houseDetailRepository.flush();
                });

        houseDetail.setId(null);
        return houseDetailRepository.save(houseDetail);
    }

    @Transactional
    public OfficeDetail saveOfficeDetail(OfficeDetail officeDetail) {
        officeDetailRepository.findByAdvertisementId(officeDetail.getAdvertisementId())
                .ifPresent(existing -> {
                    officeDetailRepository.delete(existing);
                    officeDetailRepository.flush();
                });

        officeDetail.setId(null);
        return officeDetailRepository.save(officeDetail);
    }

    @Transactional
    public ShopDetail saveShopDetail(ShopDetail shopDetail) {
        shopDetailRepository.findByAdvertisementId(shopDetail.getAdvertisementId())
                .ifPresent(existing -> {
                    shopDetailRepository.delete(existing);
                    shopDetailRepository.flush();
                });

        shopDetail.setId(null);
        return shopDetailRepository.save(shopDetail);
    }

    @Transactional
    public WarehouseDetail saveWarehouseDetail(WarehouseDetail warehouseDetail) {
        warehouseDetailRepository.findByAdvertisementId(warehouseDetail.getAdvertisementId())
                .ifPresent(existing -> {
                    warehouseDetailRepository.delete(existing);
                    warehouseDetailRepository.flush();
                });

        warehouseDetail.setId(null);
        return warehouseDetailRepository.save(warehouseDetail);
    }

    @Transactional(readOnly = true)
    public List<AdvertisementImage> getAdvertisementImages(int advertisementId) {
        return advertisementImageRepository.findByAdvertisementId(advertisementId);
    }

    @Transactional
    public List<AdvertisementImage> saveAdvertisementImages(int advertisementId, List<AdvertisementImage> images) {
        for (AdvertisementImage image : images) {
            image.setFileId(0);
            image.setAdvertisementId(advertisementId);
        }
        return advertisementImageRepository.saveAll(images);
    }

    @Transactional
    public void deleteAdvertisementImage(int fileId) {
        advertisementImageRepository.deleteById(fileId);
    }

    @Transactional
    public AdvertisementImage updateAdvertisementImageLabel(int fileId, String newName) {
        AdvertisementImage image = advertisementImageRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("Media item not found with ID: " + fileId));
        image.setName(newName);
        return advertisementImageRepository.save(image);
    }

}
