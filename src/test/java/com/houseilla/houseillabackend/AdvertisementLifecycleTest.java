package com.houseilla.houseillabackend;

import com.houseilla.houseillabackend.model.Advertiser;
import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.model.LandDetail;
import com.houseilla.houseillabackend.model.enums.AdvertiserContractual;
import com.houseilla.houseillabackend.model.enums.AdvertiserType;
import com.houseilla.houseillabackend.model.enums.AdvertisementPropertyType;
import com.houseilla.houseillabackend.model.enums.AdvertisementPurpose;
import com.houseilla.houseillabackend.model.enums.AdvertisementStatus;
import com.houseilla.houseillabackend.repository.AdvertiserRepository;
import com.houseilla.houseillabackend.repository.AdvertisementRepository;
import com.houseilla.houseillabackend.repository.LandDetailRepository;
import com.houseilla.houseillabackend.service.AdvertiserServices;
import com.houseilla.houseillabackend.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class AdvertisementLifecycleTest {

    @Autowired
    private AdvertiserServices advertiserServices;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private AdvertiserRepository advertiserRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private LandDetailRepository landDetailRepository;

    @Test
    public void verifyCompleteWizardPipelineManually() {
        log.info("==================================================");
        log.info("STARTING UNIFIED PIPELINE TEST: ADVERTISER -> AD -> LAND");
        log.info("==================================================");

        // STEP 1: Pristine Environment Initialization
        landDetailRepository.deleteAll();
        advertisementRepository.deleteAll();
        advertiserRepository.deleteAll();

        // CRITICAL FIX: Force Hibernate to execute the SQL DELETE statements immediately
        advertiserRepository.flush();
        advertisementRepository.flush();
        landDetailRepository.flush();

        log.info("Step 1: All domain tables cleared safely and flushed to H2 database.");


        // STEP 2: Wizard Stage 1 - Save Publisher Profile
        Advertiser advertiser = new Advertiser();
        advertiser.setId(0L); // 0 triggers backend service insert logic
        advertiser.setName("Pro Builders Ltd");
        advertiser.setPhone("+919876543210");
        advertiser.setEmail("contact@probuilders.in");
        advertiser.setAddress("Salt Lake Sector V, Kolkata");
        advertiser.setLocation("Kolkata");
        advertiser.setAdvertiserType(AdvertiserType.BUILDER); // Ordinal mapping
        advertiser.setContractualStatus(AdvertiserContractual.PREMIUM); // Ordinal mapping

        Advertiser savedAdvertiser = advertiserServices.saveAdvertiser(advertiser);
        assertNotNull(savedAdvertiser.getId(), "Database failed to assign primary key to Advertiser.");
        log.info("Step 2: Advertiser profile saved. Assigned ID: {}", savedAdvertiser.getId());

        // STEP 3: Wizard Stage 2 - Save Basic Advertisement Details
        Advertisement ad = new Advertisement();
        ad.setId(0L); // Trigger insert branch logic
        ad.setAdvertiserId(savedAdvertiser.getId()); // Dynamic lookup link mapping
        ad.setPropertyType(AdvertisementPropertyType.PRIVATE_LAND);
        ad.setPurpose(AdvertisementPurpose.SALE);
        ad.setPrice(new BigDecimal("6500000.00"));
        ad.setDescription("Premium corner plot available for residential development.");
        ad.setAreaName("Newtown");
        ad.setAddressLine1("Action Area II");
        ad.setDistrict("Kolkata");
        ad.setPincode("700156");
        ad.setIsPremium(1);

        Advertisement savedAd = advertisementService.saveAdvertisement(ad);
        assertNotNull(savedAd.getId(), "Database failed to assign primary key to Advertisement.");
        assertEquals(AdvertisementStatus.DRAFT, savedAd.getListingStatus(), "New advertisements must start as a DRAFT.");
        assertEquals(savedAdvertiser.getId(), savedAd.getAdvertiserId(), "Advertisement advertiserId does not match publisher primary identity.");
        log.info("Step 3: Core Advertisement saved successfully as DRAFT. Generated ID: {}", savedAd.getId());

        // STEP 4: Wizard Stage 3 - Hydrate Land Specifications Subtype
        LandDetail land = new LandDetail();
        land.setId(null); // Independent auto-increment layout primary key
        land.setAdvertisementId(savedAd.getId()); // Explicit scalar foreign identity linking
        land.setLandArea(3600); // 3600 sq.ft plot measurement profile
        land.setDescription("South-facing clear title plot bounded by boundary walls.");

        LandDetail savedLand = landDetailRepository.save(land);
        assertNotNull(savedLand.getId(), "Database failed to assign primary key to LandDetail subclass table.");
        assertEquals(savedAd.getId(), savedLand.getAdvertisementId(), "Decoupled foreign identity connection mismatch.");
        log.info("Step 4: Land physical features persisted successfully under linked Advertisement ID: {}", savedLand.getAdvertisementId());

        // STEP 5: Wizard Finalized - Publish Listing to Marketplace (Flip Status)
        savedAd.setListingStatus(AdvertisementStatus.ACTIVE);
        Advertisement activatedAd = advertisementService.saveAdvertisement(savedAd);
        assertEquals(AdvertisementStatus.ACTIVE, activatedAd.getListingStatus(), "Failed to flip listing status indicator variable to ACTIVE.");
        log.info("Step 5: Portal publication pipeline finalized successfully. Listing is now public.");

        // STEP 6: Consolidated Integration Reads Validation
        Optional<Advertiser> verifiedAdvertiser = advertiserRepository.findById(savedAdvertiser.getId());
        Optional<Advertisement> verifiedAd = advertisementRepository.findById(activatedAd.getId());
        Optional<LandDetail> verifiedLand = landDetailRepository.findByAdvertisementId(activatedAd.getId());

        assertTrue(verifiedAdvertiser.isPresent(), "Verification failure: Lost Advertiser row.");
        assertTrue(verifiedAd.isPresent(), "Verification failure: Lost Advertisement core record row.");
        assertTrue(verifiedLand.isPresent(), "Verification failure: Lost LandDetail specification component sub-table entry.");
        log.info("Step 6: Relational trace analysis complete. All distinct tables are verified cohesive.");

        // STEP 7: Comprehensive System Cleanup Lifecycle Wipe
        landDetailRepository.deleteByAdvertisementId(activatedAd.getId());
        advertisementRepository.deleteById(activatedAd.getId());
        advertiserRepository.deleteById(savedAdvertiser.getId());

        log.info("Step 7: Cascade cleanup complete. Local environment database returned safely to a pristine state.");
        log.info("==================================================");
        log.info("FULL PIPELINE INTEGRATION TEST RUN COMPLETED GREEN");
        log.info("==================================================");
    }
}
