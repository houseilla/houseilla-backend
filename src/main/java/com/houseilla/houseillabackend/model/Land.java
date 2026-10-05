package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "lands") // Explicit snake_case table name
@Data
public class Land implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "land_id")
    private int landId;

    @Column(name = "advertisement_id")
    private int advertisementId;

    private int type;

    @Column(name = "area")
    private double area;

    @Column(name = "description")
    private String description;
}
