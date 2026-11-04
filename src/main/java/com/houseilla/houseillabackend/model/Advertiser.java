package com.houseilla.houseillabackend.model;

import com.houseilla.houseillabackend.model.enums.AdvertiserContractual;
import com.houseilla.houseillabackend.model.enums.AdvertiserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "advertisers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advertiser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true, length = 15)
	private String phone;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(columnDefinition = "TEXT")
	private String address;

	private String location;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "advertiser_type", nullable = false)
	private AdvertiserType advertiserType;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "contractual_status", nullable = false)
	private AdvertiserContractual contractualStatus;
}

