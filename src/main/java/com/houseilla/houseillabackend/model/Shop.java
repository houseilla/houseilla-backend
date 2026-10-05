package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data

@Entity
@Table(name = "Shop")
public class Shop implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private int shopId;

    @Column(name = "advertisement_id")
    private int advertisementId;

    @Column(name = "floor_no")
    private String floorNo;

    @Column(name = "area")
    private String area;

}
