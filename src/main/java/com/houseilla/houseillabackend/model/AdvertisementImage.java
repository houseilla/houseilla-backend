package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Entity
public class AdvertisementImage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="file_id")
    private int fileId;
    @Column(name="advertisement_id")
    private int advertisementId;
    private String name;
    private String type;

    @Lob
    //@Column(columnDefinition = "LONGBLOB")
    private byte[] file;

    @Transient // Tells Hibernate NOT to save this calculated property to the database
    public String getBase64Image() {
        if (this.file == null || this.file.length == 0) {
            return null;
        }
        String base64Data = Base64.getEncoder().encodeToString(this.file);
        // Combines the mime-type and base64 string into a browser-readable URL format
        return "data:" + this.type + ";base64," + base64Data;
    }

}