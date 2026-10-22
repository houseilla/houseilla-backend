package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "lands")
@PrimaryKeyJoinColumn(name = "detail_id") // Joins directly to the polymorphic base table
@Data
@EqualsAndHashCode(callSuper = true)
public class Land extends PropertyDetail {

    // 1 = Private Land, 2 = Commercial Land
    @Column(name = "type")
    private int type;

    @Column(name = "area")
    private double area;

    @Column(name = "description", length = 1000)
    private String description;
}
