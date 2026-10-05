package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.io.Serial;
import java.sql.Timestamp;
import java.util.List;

@Data

@Entity
@Table(name = "advertisements")
public class Advertisement implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "advertisement_id")
	private int advertisementId;

	@Column(name = "advertiser_id")
	private int advertiserId;

	@Column(name = "advertiser_name")
	private String advertiserName;

	@Column(name = "is_premium")
	private int isPremium;

	@Column(name = "advertiser_type")
	private int advertiserType;

	@Column(name = "advertiser_phone")
	private String advertiserPhone;

	@Column(name = "advertiser_email")
	private String advertiserEmail;

	//Adress
	@Column(name = "property_number")
	private String propertyNumber;

	@Column(name = "area_name")
	private String areaName;

	@Column(name = "address_line_1")
	private String addressLine1;

	@Column(name = "address_line_2")
	private String addressLine2;

	@Column(name = "district")
	private String district;

	@Column(name = "postal_code")
	private String pincode;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	//Advertisement Type and Purpose

	@Column(name = "category")
	private int category = -1;

	@Column(name = "property_type")
	private int propertyType = -1;

	@Column(name = "purpose")
	private int purpose = -1;

	@Column(name = "price")
	private double price;

	@Column(name = "description")
	private String description;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "create_date")
	private Timestamp createDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "advertisement_id") // Maps to the foreign key column in ADVERTISEMENT_IMAGE table
	private List<AdvertisementImage> images;

}

