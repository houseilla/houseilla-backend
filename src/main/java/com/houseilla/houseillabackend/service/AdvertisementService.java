package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.model.AdvertisementImage;
import com.houseilla.houseillabackend.model.FlatDetail;
import com.houseilla.houseillabackend.model.HouseDetail;
import com.houseilla.houseillabackend.model.LandDetail;
import com.houseilla.houseillabackend.model.OfficeDetail;
import com.houseilla.houseillabackend.model.ShopDetail;
import com.houseilla.houseillabackend.model.WarehouseDetail;
import com.houseilla.houseillabackend.model.enums.AdvertisementPurpose;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public Page<Advertisement> searchActiveAdvertisements(
            Integer purpose,
            List<Integer> types,
            String budget,
            String pincode,
            List<String> pincodes,
            String q,
            int page,
            int size
    ) {
        log.info("Searching active advertisements | purpose={}, types={}, budget={}, pincode={}, pincodes={}, q={}, page={}, size={}",
                purpose, types, budget, pincode, pincodes, q, page, size);

        String trimmedPin = (pincode != null) ? pincode.trim() : "";
        String searchLower = (q != null) ? q.trim().toLowerCase() : "";
        List<String> cleanPincodes = (pincodes != null)
                ? pincodes.stream().map(String::trim).filter(s -> !s.isEmpty()).toList()
                : List.of();

        List<Advertisement> matched = advertisementRepository.findAll().stream()
                .filter(ad -> ad.getListingStatus() == AdvertisementStatus.ACTIVE)
                .filter(ad -> purpose == null || (ad.getPurpose() != null && ad.getPurpose().ordinal() == purpose))
                .filter(ad -> types == null || types.isEmpty()
                        || (ad.getPropertyType() != null && types.contains(ad.getPropertyType().ordinal())))
                .filter(ad -> matchesBudgetTier(ad, purpose, budget))
                .filter(ad -> matchesLocation(ad, trimmedPin, cleanPincodes, searchLower))
                .sorted((a, b) -> {
                    int premiumCompare = Integer.compare(b.getIsPremium(), a.getIsPremium());
                    if (premiumCompare != 0) return premiumCompare;
                    return Long.compare(b.getId() != null ? b.getId() : 0L, a.getId() != null ? a.getId() : 0L);
                })
                .toList();

        int safePage = Math.max(page, 0);
        int safeSize = Math.max(size, 1);
        Pageable pageable = PageRequest.of(safePage, safeSize);

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), matched.size());
        List<Advertisement> pageContent = (start >= matched.size()) ? List.of() : matched.subList(start, end);

        return new PageImpl<>(pageContent, pageable, matched.size());
    }

    private boolean matchesBudgetTier(Advertisement ad, Integer purpose, String budget) {
        if (budget == null || budget.isBlank() || ad.getPrice() == null) {
            return true;
        }
        double price = ad.getPrice().doubleValue();
        boolean isSale = (purpose != null && purpose == AdvertisementPurpose.SALE.ordinal())
                || (purpose == null && ad.getPurpose() == AdvertisementPurpose.SALE);

        if (isSale) {
            double lakhs = price / 100000.0;
            return switch (budget.trim()) {
                case "1" -> lakhs < 50.0;
                case "2" -> lakhs >= 50.0 && lakhs <= 100.0;
                case "3" -> lakhs > 100.0 && lakhs <= 300.0;
                case "4" -> lakhs > 300.0;
                default -> true;
            };
        } else {
            return switch (budget.trim()) {
                case "1" -> price < 15000.0;
                case "2" -> price >= 15000.0 && price <= 35000.0;
                case "3" -> price > 35000.0 && price <= 75000.0;
                case "4" -> price > 75000.0;
                default -> true;
            };
        }
    }

    private boolean matchesLocation(Advertisement ad, String exactPin, List<String> pincodes, String q) {
        String adPin = ad.getPincode() != null ? ad.getPincode().trim() : "";

        if (!exactPin.isEmpty()) {
            return adPin.equals(exactPin);
        }

        if (!q.isEmpty() || !pincodes.isEmpty()) {
            boolean pinMatch = !pincodes.isEmpty() && pincodes.contains(adPin);
            boolean textMatch = false;
            if (!q.isEmpty()) {
                textMatch = (ad.getAreaName() != null && ad.getAreaName().toLowerCase().contains(q))
                        || (ad.getAddressLine1() != null && ad.getAddressLine1().toLowerCase().contains(q))
                        || (ad.getAddressLine2() != null && ad.getAddressLine2().toLowerCase().contains(q))
                        || (ad.getDistrict() != null && ad.getDistrict().toLowerCase().contains(q))
                        || adPin.contains(q);
            }
            return pinMatch || textMatch;
        }

        return true;
    }

    @Transactional(readOnly = true)
    public Advertisement getAdvertisementById(Long id) {
        return advertisementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Advertisement record with ID " + id + " not found."));
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
