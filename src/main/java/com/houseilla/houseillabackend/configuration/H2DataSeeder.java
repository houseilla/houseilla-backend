package com.houseilla.houseillabackend.configuration;

import com.houseilla.houseillabackend.model.*;
import com.houseilla.houseillabackend.model.enums.*;
import com.houseilla.houseillabackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class H2DataSeeder implements CommandLineRunner {

    private final AdvertiserRepository advertiserRepository;
    private final AdvertisementRepository advertisementRepository;
    private final FlatDetailRepository flatDetailRepository;
    private final HouseDetailRepository houseDetailRepository;
    private final LandDetailRepository landDetailRepository;
    private final OfficeDetailRepository officeDetailRepository;
    private final ShopDetailRepository shopDetailRepository;
    private final WarehouseDetailRepository warehouseDetailRepository;

    @Override
    @Transactional
    public void run(String... args) {

        boolean sampleAdvertiserExists = advertiserRepository.findAll().stream()
                .anyMatch(a -> "subrata.chatterjee@example.com".equalsIgnoreCase(a.getEmail()));
        if (sampleAdvertiserExists) {
            return;
        }

        // 1. Seed Advertisers (Individual, Builder, Broker)
        Advertiser individualOwner = new Advertiser();
        individualOwner.setName("Subrata Chatterjee");
        individualOwner.setPhone("9830112233");
        individualOwner.setEmail("subrata.chatterjee@example.com");
        individualOwner.setAddress("BD-142, Sector I, Salt Lake, Kolkata");
        individualOwner.setLocation("Kolkata");
        individualOwner.setAdvertiserType(AdvertiserType.INDIVIDUAL);
        individualOwner.setContractualStatus(AdvertiserContractual.values()[0]);
        individualOwner = advertiserRepository.save(individualOwner);

        Advertiser verifiedBuilder = new Advertiser();
        verifiedBuilder.setName("Bengal Skyline Developers");
        verifiedBuilder.setPhone("9831099887");
        verifiedBuilder.setEmail("sales@bengalskyline.in");
        verifiedBuilder.setAddress("DN-36, Sector V, Salt Lake, Kolkata");
        verifiedBuilder.setLocation("Kolkata");
        verifiedBuilder.setAdvertiserType(AdvertiserType.BUILDER);
        verifiedBuilder.setContractualStatus(
                AdvertiserContractual.values().length > 1
                        ? AdvertiserContractual.values()[1]
                        : AdvertiserContractual.values()[0]
        );
        verifiedBuilder = advertiserRepository.save(verifiedBuilder);

        Advertiser localBroker = new Advertiser();
        localBroker.setName("Ananya Sen Properties");
        localBroker.setPhone("9007654321");
        localBroker.setEmail("ananya.sen@example.com");
        localBroker.setAddress("42 Gariahat Road, South Kolkata");
        localBroker.setLocation("Kolkata");
        localBroker.setAdvertiserType(AdvertiserType.BROKER);
        localBroker.setContractualStatus(AdvertiserContractual.values()[0]);
        localBroker = advertiserRepository.save(localBroker);

        // =====================================================================
        // PRIVATE PROPERTIES (FLATS)
        // =====================================================================

        // 1. Flat for Sale — Survey Park / Santoshpur (Under 50 Lakh)
        Advertisement flat1 = createAd(
                individualOwner.getId(),
                AdvertisementPropertyType.FLAT,
                AdvertisementPurpose.SALE,
                AdvertisementStatus.ACTIVE,
                new BigDecimal("4650000.00"),
                "South-facing 2 BHK flat in a peaceful residential lane near Survey Park, 5 mins from EM Bypass.",
                "Premise 18/A",
                "Survey Park",
                "Santoshpur Main Road",
                "Near Kishore Bharati Stadium",
                "Kolkata",
                "700075",
                new BigDecimal("22.4952000"),
                new BigDecimal("88.3918000"),
                0
        );
        FlatDetail fd1 = new FlatDetail();
        fd1.setAdvertisementId(flat1.getId());
        fd1.setCommunity(false);
        fd1.setBuildingRising("G+4");
        fd1.setFloorNumber(2);
        fd1.setCarpetArea(880);
        fd1.setFlatType("2 BHK");
        fd1.setLiftAvailable(true);
        fd1.setParkings(1);
        fd1.setPossessionBy(LocalDate.of(2026, 12, 1));
        fd1.setPropertyAge(3);
        fd1.setAmenities("Lift, Power Backup, CCTV Security, Water Storage");
        flatDetailRepository.save(fd1);

        // 2. Premium Flat for Sale — New Town Action Area 1 (50 L - 1 Cr)
        Advertisement flat2 = createAd(
                verifiedBuilder.getId(),
                AdvertisementPropertyType.FLAT,
                AdvertisementPurpose.SALE,
                AdvertisementStatus.ACTIVE,
                new BigDecimal("8200000.00"),
                "Spacious 3 BHK apartment in a gated complex in New Town Action Area 1 with clubhouse and covered parking.",
                "Tower B, Flat 704",
                "New Town",
                "Action Area 1B, Street 112",
                "Opposite Axis Mall",
                "North 24 Parganas",
                "700156",
                new BigDecimal("22.5816000"),
                new BigDecimal("88.4598000"),
                1
        );
        FlatDetail fd2 = new FlatDetail();
        fd2.setAdvertisementId(flat2.getId());
        fd2.setCommunity(true);
        fd2.setBuildingRising("G+14");
        fd2.setFloorNumber(7);
        fd2.setCarpetArea(1340);
        fd2.setFlatType("3 BHK");
        fd2.setLiftAvailable(true);
        fd2.setParkings(1);
        fd2.setPossessionBy(LocalDate.of(2026, 11, 30));
        fd2.setPropertyAge(0);
        fd2.setAmenities("Lift, Swimming Pool, Gymnasium, Clubhouse, 24x7 Security, Power Backup");
        flatDetailRepository.save(fd2);

        // 3. Flat for Rent — Salt Lake Sector I (₹15k - ₹35k)
        Advertisement flat3 = createAd(
                individualOwner.getId(),
                AdvertisementPropertyType.FLAT,
                AdvertisementPurpose.RENT,
                AdvertisementStatus.ACTIVE,
                new BigDecimal("24000.00"),
                "Well-ventilated 2 BHK flat ideal for IT professionals working in Salt Lake Sector V and Technopolis.",
                "AE-210",
                "Salt Lake",
                "Sector I, AE Block",
                "Near BF Swimming Pool",
                "North 24 Parganas",
                "700064",
                new BigDecimal("22.5867000"),
                new BigDecimal("88.4171000"),
                0
        );
        FlatDetail fd3 = new FlatDetail();
        fd3.setAdvertisementId(flat3.getId());
        fd3.setCommunity(false);
        fd3.setBuildingRising("G+3");
        fd3.setFloorNumber(1);
        fd3.setCarpetArea(950);
        fd3.setFlatType("2 BHK");
        fd3.setLiftAvailable(false);
        fd3.setParkings(1);
        fd3.setPossessionBy(LocalDate.of(2026, 11, 20));
        fd3.setPropertyAge(6);
        fd3.setAmenities("24x7 Water Supply, Balcony, Bike & Car Parking");
        flatDetailRepository.save(fd3);
        // 4. Independent House for Sale — Ballygunge (1 Cr - 3 Cr)
        Advertisement house1 = createAd(
                verifiedBuilder.getId(), 
                AdvertisementPropertyType.HOUSE,
                AdvertisementPurpose.SALE,
                AdvertisementStatus.ACTIVE,
                new BigDecimal("24500000.00"),
                "G+2 independent residential house with private terrace and 2 covered car parkings in prime Ballygunge.",
                "Plot 14B",
                "Ballygunge",
                "Ballygunge Place East",
                "Near Triangular Park",
                "Kolkata",
                "700019",
                new BigDecimal("22.5267000"),
                new BigDecimal("88.3665000"),
                1
        );
        HouseDetail hd1 = new HouseDetail();
        hd1.setAdvertisementId(house1.getId());
        hd1.setLandArea(2880);
        hd1.setCarpetArea(3200);
        hd1.setFloors(3);
        hd1.setNoOfParking(2);
        hd1.setRoadFacing("South");
        hd1.setRoadWidth(24);
        houseDetailRepository.save(hd1);

        // 5. Private Residential Land for Sale — Sonarpur (Under 50 Lakh)
        Advertisement land1 = createAd(
                localBroker.getId(),
                AdvertisementPropertyType.PRIVATE_LAND,
                AdvertisementPurpose.SALE,
                AdvertisementStatus.ACTIVE,
                new BigDecimal("3200000.00"),
                "Boundary-walled Bastu residential plot 10 mins from Sonarpur Station with 20 ft concrete approach road.",
                "Dag 412",
                "Sonarpur",
                "Kamalgazi-Sonarpur Road",
                "Near Mission Pally",
                "South 24 Parganas",
                "700150",
                new BigDecimal("22.4491000"),
                new BigDecimal("88.4256000"),
                0
        );
        LandDetail ld1 = new LandDetail();
        ld1.setAdvertisementId(land1.getId());
        ld1.setLandArea(2160);
        ld1.setDescription("Clear title Bastu land, boundary wall completed, electricity and municipality water line adjacent.");
        landDetailRepository.save(ld1);

        // =====================================================================
        // COMMERCIAL PROPERTIES (OFFICE & SHOP)
        // =====================================================================

        // 6. Furnished IT Office for Rent — Salt Lake Sector V (₹35k - ₹75k)
        Advertisement office1 = createAd(
                verifiedBuilder.getId(),
                AdvertisementPropertyType.OFFICE,
                AdvertisementPurpose.RENT,
                AdvertisementStatus.ACTIVE,
                new BigDecimal("65000.00"),
                "Plug-and-play furnished IT office space with 22 workstations, 2 cabins, conference room, and pantry.",
                "Suite 502, Globsyn Plaza",
                "Salt Lake Sector V",
                "EP & GP Block, Sector V",
                "Near College More",
                "North 24 Parganas",
                "700091",
                new BigDecimal("22.5735000"),
                new BigDecimal("88.4331000"),
                1
        );
        OfficeDetail od1 = new OfficeDetail();
        od1.setAdvertisementId(office1.getId());
        od1.setFloorNo(5);
        od1.setArea(1650);
        od1.setFurnishedType("Fully Furnished");
        officeDetailRepository.save(od1);

        // 7. Retail Shop for Sale — Gariahat (1 Cr - 3 Cr)
        Advertisement shop1 = createAd(
                localBroker.getId(),
                AdvertisementPropertyType.SHOP,
                AdvertisementPurpose.SALE,
                AdvertisementStatus.ACTIVE,
                new BigDecimal("14000000.00"),
                "High-footfall ground floor main-road showroom/shop near Gariahat crossing with wide glass frontage.",
                "Shop G-04",
                "Gariahat",
                "Rashbehari Avenue",
                "Near Gariahat Crossing",
                "Kolkata",
                "700029",
                new BigDecimal("22.5176000"),
                new BigDecimal("88.3654000"),
                1
        );
        ShopDetail sd1 = new ShopDetail();
        sd1.setAdvertisementId(shop1.getId());
        sd1.setFloorNo(0);
        sd1.setArea(620);
        shopDetailRepository.save(sd1);
        // 8. Commercial Land for Sale — Rajarhat Main Road (Above 3 Cr)
        Advertisement commLand1 = createAd(
                verifiedBuilder.getId(),
                AdvertisementPropertyType.COMMERCIAL_LAND,
                AdvertisementPurpose.SALE,
                AdvertisementStatus.ACTIVE,
                new BigDecimal("38500000.00"),
                "Prime 10 Katha commercial corner plot on Rajarhat Main Road suitable for hospital, hotel, or corporate hub.",
                "Plot C-19",
                "Rajarhat",
                "Rajarhat Main Road, Chinar Park",
                "Near City Centre 2",
                "North 24 Parganas",
                "700135",
                new BigDecimal("22.6251000"),
                new BigDecimal("88.4442000"),
                1
        );
        LandDetail ld2 = new LandDetail();
        ld2.setAdvertisementId(commLand1.getId());
        ld2.setLandArea(7200);
        ld2.setDescription("40 ft wide main road frontage, commercial conversion approved, immediate possession.");
        landDetailRepository.save(ld2);

        // 9. Industrial Warehouse for Lease — Dankuni (Above ₹75k)
        Advertisement warehouse1 = createAd(
                localBroker.getId(),
                AdvertisementPropertyType.WAREHOUSE,
                AdvertisementPurpose.LEASE,
                AdvertisementStatus.ACTIVE,
                new BigDecimal("135000.00"),
                "PEB industrial godown with 28 ft center height, VDF flooring, and 40-ft container trailer docking bay on NH-19.",
                "Shed 3, Logistics Park",
                "Dankuni",
                "Delhi Road (NH-19)",
                "Near Dankuni Toll Plaza",
                "Hooghly",
                "712311",
                new BigDecimal("22.6745000"),
                new BigDecimal("88.2974000"),
                0
        );
        WarehouseDetail wd1 = new WarehouseDetail();
        wd1.setAdvertisementId(warehouse1.getId());
        wd1.setArea(8500);
        warehouseDetailRepository.save(wd1);
    }

    private Advertisement createAd(
            Long advertiserId,
            AdvertisementPropertyType propertyType,
            AdvertisementPurpose purpose,
            AdvertisementStatus status,
            BigDecimal price,
            String description,
            String propertyNumber,
            String areaName,
            String addressLine1,
            String addressLine2,
            String district,
            String pincode,
            BigDecimal latitude,
            BigDecimal longitude,
            int isPremium
    ) {
        Advertisement ad = new Advertisement();
        ad.setAdvertiserId(advertiserId);
        ad.setPropertyType(propertyType);
        ad.setPurpose(purpose);
        ad.setListingStatus(status);
        ad.setPrice(price);
        ad.setDescription(description);
        ad.setPropertyNumber(propertyNumber);
        ad.setAreaName(areaName);
        ad.setAddressLine1(addressLine1);
        ad.setAddressLine2(addressLine2);
        ad.setDistrict(district);
        ad.setPincode(pincode);
        ad.setLatitude(latitude);
        ad.setLongitude(longitude);
        ad.setIsPremium(isPremium);
        return advertisementRepository.save(ad);
    }
}
