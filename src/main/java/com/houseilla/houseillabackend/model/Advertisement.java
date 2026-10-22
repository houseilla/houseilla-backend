package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "advertisements")
@Data // Generates getters, setters, toString, equals, and hashCode automatically
public class Advertisement implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "advertisement_id")
	private int advertisementId;

	// RULE 1: Clean foreign key relationship to the real Advertiser entity
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "advertiser_id", nullable = false)
	private Advertiser advertiser;

	// RULE 2: Managed relationship to an optional list of structural images
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "advertisement_id")
	private List<AdvertisementImage> images;

	// RULE 3 & 4: Core Shared Categorization Fields
	@Column(name = "category")
	private int category;     // 1 = Private, 2 = Commercial

	@Column(name = "property_type")
	private int propertyType; // Sub-type ID mapping indicator

	// 🌟 THE UNIFIED REPLACEMENT FIELD FOR THE 6 SUB-TYPES
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "property_detail_id")
	private PropertyDetail propertyDetail;

	// Core Baseline Properties
	@Column(name = "price")
	private double price;

	@Column(name = "description", length = 1000)
	private String description;

	// Address & Geolocation Context
	@Column(name = "property_number")
	private String propertyNumber;

	@Column(name = "area_name")
	private String areaName;

	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;

	@Column(name = "district")
	private String district;

	@Column(name = "pincode")
	private String pincode;

	@Column(name = "latitude")
	private double latitude;

	@Column(name = "longitude")
	private double longitude;

	// Premium Feature Flag
	@Column(name = "is_premium")
	private int isPremium;

	// System Timestamps
	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "create_date")
	private Timestamp createDate;
}
