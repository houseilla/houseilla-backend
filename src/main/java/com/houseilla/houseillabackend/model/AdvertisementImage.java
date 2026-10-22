package com.houseilla.houseillabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Base64;

@Data
@Entity
@Table(name = "advertisement_images")
public class AdvertisementImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private int fileId;

    @Column(name = "advertisement_id")
    private int advertisementId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file; // Standardized variable name to match the encoder helper below

    /**
     * Tells Hibernate NOT to save this calculated property to the database layout.
     * Combines the mime-type and base64 string into a browser-readable data URL.
     */
    @Transient
    public String getBase64Data() {
        if (this.file == null || this.file.length == 0) {
            return null;
        }
        String base64Data = Base64.getEncoder().encodeToString(this.file);
        return "data:" + this.type + ";base64," + base64Data;
    }
}
