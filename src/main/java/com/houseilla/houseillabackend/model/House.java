package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Data

@Entity
@Table(name = "Houses")
public class House implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private int houseId;

    @Column(name = "advertisement_id")
    private int advertisementId;

    @Column(name = "land_area")
    private String landArea;

    @Column(name = "carpet_area")
    private String carpetArea; 

    @Column(name = "floors")
    private int floors;

    @Column(name = "parkings")
    private int parkings;

    @Column(name = "no_of_parking")
    private int noOfParking;

    @Column(name = "road_facing")
    private int roadFacing;

    @Column(name = "road_width")
    private int roadWidth;

}
