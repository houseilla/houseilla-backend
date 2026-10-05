package com.houseilla.houseillabackend.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AdvertisementePurpose {
    SALE(0),
    RENT(1),
    LEASE(2);

    private final int value;

    AdvertisementePurpose(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}
