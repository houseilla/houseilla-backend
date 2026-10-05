package com.houseilla.houseillabackend.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AdvertisementCategory {
    PRIVATE(0),
    COMMERCIAL(1);

    private final int value;

    AdvertisementCategory(int value) {
        this.value = value;
    }

    @JsonValue // Tells Jackson to serialize this enum as a raw number for Angular
    public int getValue() {
        return value;
    }
}
