package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "house_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "advertisement_id", nullable = false, unique = true)
    private Long advertisementId;

    @Column(name = "land_area")
    private Integer landArea;

    @Column(name = "carpet_area")
    private Integer carpetArea;

    @Column(name = "floors")
    private Integer floors;

    @Column(name = "parkings")
    private Integer parkings;

    @Column(name = "no_of_parking")
    private Integer noOfParking;

    @Column(name = "road_facing")
    private String roadFacing;

    @Column(name = "road_width")
    private Integer roadWidth;
}
