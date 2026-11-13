package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "flat_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlatDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "advertisement_id", nullable = false, unique = true)
    private Long advertisementId;

    @Column(name = "is_community", nullable = false)
    private boolean isCommunity;

    @Column(name = "building_rising")
    private String buildingRising;

    @Column(name = "floor_number")
    private Integer floorNumber;

    @Column(name = "carpet_area")
    private Integer carpetArea;

    @Column(name = "flat_type")
    private String flatType;

    @Column(name = "lift_available", nullable = false)
    private boolean liftAvailable;

    @Column(name = "parkings")
    private Integer parkings;

    @Column(name = "possession_by")
    private LocalDate possessionBy;

    @Column(name = "property_age")
    private Integer propertyAge;

    @Column(name = "amenities", length = 1000)
    private String amenities;
}
