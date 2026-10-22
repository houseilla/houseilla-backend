package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Office")
@PrimaryKeyJoinColumn(name = "detail_id") // Automatically joins subclass schema fields to the base table
@Data
@EqualsAndHashCode(callSuper = true)
public class Office extends PropertyDetail {

    @Column(name = "floor_no")
    private String floorNo;

    @Column(name = "area")
    private String area;

    @Column(name = "furnished_type")
    private int furnishedType;
}
