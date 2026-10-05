package com.houseilla.houseillabackend.service;

import com.houseilla.houseillabackend.model.AdvertisementImage;
import com.houseilla.houseillabackend.repository.AdvertisementImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AdvertisementImageServices {

    @Autowired
    AdvertisementImages advertisementImagesRepository;

    public void saveAdvetisementImages(int advertisementId, MultipartFile file) throws IOException {

        AdvertisementImage image = new AdvertisementImage();
        image.setAdvertisementId(advertisementId);
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setFile(file.getBytes()); // Binary data stored as BLOB

        advertisementImagesRepository.save(image);

    }

    public List<AdvertisementImage> getAdvertisementImages(int advertisementId) {
        return advertisementImagesRepository.findByAdvertisementId(advertisementId);
    }
}
