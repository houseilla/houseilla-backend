package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "enquiries")
@Data
public class Enquiry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "enquiry_id")
    private Long enquiryId;

    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;

    @Column(name = "advertiser_id")
    private Long advertiserId;
    @Column(name = "advertiser_name")
    private String advertiserName;
    @Column(name = "is_premium")
    private String isPremium;
    @Column(name = "advertiser_type")
    private String advertiserType;
    @Column(name = "advertiser_phone")
    private String advertiserPhone;
    @Column(name = "advertiser_email")
    private String advertiserEmail;

    @Column(name = "advertisement_id")
    private Long advertisementId;
    @Column(name = "property_number")
    private String propertyNumber;
    @Column(name = "street")
    private String street;
    @Column(name = "area_name")
    private String areaName;
    @Column(name = "district")
    private String district;
    @Column(name = "pincode")
    private String pinCode;
    @Column(name = "category")
    private String category;
    @Column(name = "property_type")
    private String propertyType;
    @Column(name = "purpose")
    private String purpose;
    @Column(name = "price")
    private Double price;
    @Column(name = "description")
    private String description;
}
