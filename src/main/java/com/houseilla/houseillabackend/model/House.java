package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Houses")
@PrimaryKeyJoinColumn(name = "detail_id") // Automatically joins subclass schema fields to the base table
@Data
@EqualsAndHashCode(callSuper = true)
public class House extends PropertyDetail {

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
