package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Data

@Entity
@Table(name = "Flats")
public class Flat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flat_id")
    private int flatId;

    @Column(name = "advertisement_id")
    private int advertisementId;

    @Column(name = "is_resale")
    private int isResale = -1;

    @Column(name = "is_community")
    private int isCommunity = -1;

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

    @Column(name = "aminities")
    private String aminities;

}
