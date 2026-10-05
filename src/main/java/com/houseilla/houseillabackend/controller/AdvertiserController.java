package com.houseilla.houseillabackend.controller;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.houseilla.houseillabackend.model.Advertisement;
import com.houseilla.houseillabackend.model.Advertiser;
import com.houseilla.houseillabackend.service.AdvertisementService;
import com.houseilla.houseillabackend.service.AdvertiserServices;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class AdvertiserController {
	
	@Autowired
	AdvertiserServices advertiserServices;

	@GetMapping("advertiser/{phoneNo}")
	public ResponseEntity<Advertiser> getAdvertiserByPhoneNo(@PathVariable String phoneNo) {
		log.info("[getAdvertiserByPhoneNo] Phone Number : {}", phoneNo);
		
		Advertiser advertiser = advertiserServices.getAdvertiserByPhoneNo(phoneNo);

        if(Objects.nonNull(advertiser))
            log.info("[getAdvertiserByPhoneNo] Returning value : {} ", advertiser.toString());
        else
            log.info("No advertiser exists with phone No {} ", phoneNo);

		return ResponseEntity.ok().body(advertiser);
	}
	
	@PostMapping("advertiser")
	public ResponseEntity<Advertiser> saveAdvertiser(@RequestBody Advertiser advertiser) {

        log.info("Advertiser to save : {} ", advertiser);
        Advertiser savedAdvertiser = advertiserServices.saveAdvertiser(advertiser);

        if(Objects.nonNull(savedAdvertiser))
            log.info("[saveAdvertiser] Returning value : {} ", savedAdvertiser.toString());
        else
            log.info("advertiser can not be saved ");
		return ResponseEntity.ok().body(savedAdvertiser);
	}

    @GetMapping("advertisers")
    public List<Advertiser> getAllAdvertisers(){
        List<Advertiser> advertisers = advertiserServices.allAdvertisers();
        return advertisers;
    }

/* Advertisement

	@PostMapping("advertiser/{id}/photos")
	public String uploadAdvertiserPhotos(
			@PathVariable int id,
			@RequestParam("photos") List<MultipartFile> photos) throws IOException {
		List<byte[]> photoBytes = new ArrayList<>();
		for (MultipartFile photo : photos) {
			photoBytes.add(photo.getBytes());
		}
		advertiserServices.updateAdvertiserPhotos(id, photoBytes);
		return "Photos uploaded successfully";
	}


	@GetMapping("advertiser/{id}/photos")
	public ResponseEntity<List<String>> getAllAdvertiserPhotos(@PathVariable int id) {
		Advertiser advertiser = advertiserServices.getAdvertiserByAdvertiserId(id);
		if (advertiser == null || advertiser.getPhotos() == null) {
			return ResponseEntity.notFound().build();
		}
		List<String> dataUrls = new ArrayList<>();
		for (byte[] photo : advertiser.getPhotos()) {
			String base64 = java.util.Base64.getEncoder().encodeToString(photo);
			dataUrls.add("data:image/jpeg;base64," + base64);
		}
		return ResponseEntity.ok(dataUrls);
	}

	@GetMapping("advertiser/{id}/photo/{index}")
	public ResponseEntity<byte[]> getAdvertiserPhoto(
			@PathVariable int id,
			@PathVariable int index) {
		Advertiser advertiser = advertiserServices.getAdvertiserByAdvertiserId(id);
		if (advertiser == null || advertiser.getPhotos() == null || index < 0 || index >= advertiser.getPhotos().size()) {
			return ResponseEntity.notFound().build();
		}
		byte[] photo = advertiser.getPhotos().get(index);
		return ResponseEntity
				.ok()
				.header("Content-Type", "image/jpeg")
				.body(photo);
	}

	// Java
	@DeleteMapping("advertiser/{id}/photos")
	public ResponseEntity<String> deleteAdvertiserPhotos(@PathVariable int id) {
		Advertiser advertiser = advertiserServices.getAdvertiserByAdvertiserId(id);
		if (advertiser == null || advertiser.getPhotos() == null) {
			return ResponseEntity.notFound().build();
		}
		advertiser.setPhotos(new ArrayList<>());
		advertiserServices.saveAdvertiser(advertiser);
		return ResponseEntity.ok("Photos deleted successfully");
	}

 */

}
