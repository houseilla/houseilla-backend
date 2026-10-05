package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data

@Entity
@Table(name = "Warehouse")
public class Warehouse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private int warehouseId;

    @Column(name = "advertisement_id")
    private int advertisementId;

    @Column(name = "area")
    private String area;

}
