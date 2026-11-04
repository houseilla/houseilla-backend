package com.houseilla.houseillabackend.model;

import com.houseilla.houseillabackend.model.enums.AdvertisementPropertyType;
import com.houseilla.houseillabackend.model.enums.AdvertisementPurpose;
import com.houseilla.houseillabackend.model.enums.AdvertisementStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "advertisements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "advertiser_id", nullable = false)
	private Long advertiserId;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "property_type", nullable = false)
	private AdvertisementPropertyType propertyType;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "purpose", nullable = false)
	private AdvertisementPurpose purpose;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "listing_status", nullable = false)
	private AdvertisementStatus listingStatus = AdvertisementStatus.DRAFT;

	@Column(name = "price", nullable = false, precision = 15, scale = 2)
	private BigDecimal price;

	@Column(name = "description", length = 1000)
	private String description;

	@Column(name = "property_number")
	private String propertyNumber;

	@Column(name = "area_name", nullable = false)
	private String areaName;

	@Column(name = "address_line_1", nullable = false)
	private String addressLine1;

	@Column(name = "address_line_2")
	private String addressLine2;

	@Column(name = "district")
	private String district;

	@Column(name = "pincode", length = 10)
	private String pincode;

	@Column(name = "latitude", precision = 10, scale = 7)
	private BigDecimal latitude;

	@Column(name = "longitude", precision = 10, scale = 7)
	private BigDecimal longitude;

	@Column(name = "is_premium", nullable = false)
	private int isPremium = 0; // 0 = Standard, 1 = Premium Feature Listing

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@Column(name = "create_date", updatable = false)
	private LocalDateTime createDate;

	@PrePersist
	protected void onCreate() {
		this.createDate = LocalDateTime.now();
		this.updateDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updateDate = LocalDateTime.now();
	}
}
