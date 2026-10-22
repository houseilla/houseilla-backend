package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Flats")
@PrimaryKeyJoinColumn(name = "detail_id") // Automatically joins subclass fields to the base table
@Data
@EqualsAndHashCode(callSuper = true)
public class Flat extends PropertyDetail {

    @Column(name = "is_rent")
    private int isRent;

    @Column(name = "is_community")
    private int isCommunity;

    @Column(name = "building_rising")
    private int buildingRising;

    @Column(name = "floor_number")
    private int floorNumber;

    @Column(name = "carpet_area")
    private String carpetArea;

    @Column(name = "flat_type")
    private int flatType;

    @Column(name = "lift_available")
    private int liftAvailable;

    @Column(name = "parkings")
    private int parkings;

    @Column(name = "possession_by")
    private String possessionBy;

    @Column(name = "property_age")
    private int propertyAge;

    @Column(name = "amenities", length = 1000)
    private String amenities;
}
