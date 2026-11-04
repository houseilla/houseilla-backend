package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "land_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "advertisement_id", nullable = false, unique = true)
    private Long advertisementId;

    @Column(name = "land_area", nullable = false)
    private Integer landArea;

    @Column(name = "description", length = 1000)
    private String description;
}
