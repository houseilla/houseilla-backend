package com.houseilla.houseillabackend.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;


@Data

@Entity
@Table(name = "Advertisers")
public class Advertiser implements Serializable {

	@Serial
    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "advertiser_id")
	private int advertiserId;

	@Column
	private String name;

	@Column(unique = true)
	private String phone;

	@Column
	private String email;

	@Column
	private String address;

	@Column
	private String location;

	@Column
	private int type;

	@Column
	private int contractual;

}
