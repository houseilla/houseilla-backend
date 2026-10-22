package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "Advertisers")
public class Advertiser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "advertiser_id")
	private int advertiserId;

	@Column(name = "name")
	private String name;

	@Column(name = "phone", unique = true)
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "address")
	private String address;

	@Column(name = "location")
	private String location;

	@Column(name = "type")
	private int type;

	@Column(name = "contractual")
	private int contractual;

	// Bidirectional visibility tracking all listings published by this individual profile
	@OneToMany(mappedBy = "advertiser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Advertisement> advertisements;
}
