package com.houseilla.houseillabackend.model;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Data

@Entity
@Table(name = "Office")
public class Office implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "office_id")
    private int officeId;

    @Column(name = "advertisement_id")
    private int advertisementId;

    @Column(name = "floor_no")
    private String floorNo;

    @Column(name = "area")
    private String area;

    @Column(name = "furnished_type")
    private int furnishedType;

}
