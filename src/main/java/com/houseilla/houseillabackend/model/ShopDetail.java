package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "shop_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "advertisement_id", nullable = false, unique = true)
    private Long advertisementId;

    @Column(name = "floor_no")
    private Integer floorNo;

    @Column(name = "area")
    private Integer area;
}
