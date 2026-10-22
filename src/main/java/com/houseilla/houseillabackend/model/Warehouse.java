package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Warehouse")
@PrimaryKeyJoinColumn(name = "detail_id") // Joins this subclass schema table to the base PropertyDetail table
@Data
@EqualsAndHashCode(callSuper = true)
public class Warehouse extends PropertyDetail {

    @Column(name = "area")
    private String area;
}
