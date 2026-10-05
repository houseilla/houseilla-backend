package com.houseilla.houseillabackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.houseilla.houseillabackend.model.Advertiser;
import com.houseilla.houseillabackend.repository.AdvertiserRepository;

@Service
public class AdvertiserServices {
	
	@Autowired 
	AdvertiserRepository advertiserRepository;

	public AdvertiserServices() {}
	
	public Advertiser getAdvertiserByPhoneNo(String phoneNo) {
        Advertiser advertiser = advertiserRepository.findByPhone(phoneNo);
		return advertiser;
	}
	
	public Advertiser getAdvertiserByAdvertiserId(int advertiser_id) {
		return (Advertiser) advertiserRepository.findByAdvertiserId(advertiser_id);
	}

	public Advertiser saveAdvertiser(Advertiser advertiser) {
		return advertiserRepository.save(advertiser);
	}
	
	public List<Advertiser> allAdvertisers() {
		return advertiserRepository.findAll();
	}

	// Java
	@Transactional
	public void updateAdvertiserPhotos(int advertiserId, List<byte[]> photos) {
		Advertiser advertiser = advertiserRepository.findByAdvertiserId(advertiserId);
		if (advertiser != null) {
			//advertiser.setPhotos(photos);
			advertiserRepository.save(advertiser);
		}
	}
}
