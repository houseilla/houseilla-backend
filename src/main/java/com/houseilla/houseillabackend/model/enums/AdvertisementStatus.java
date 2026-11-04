package com.houseilla.houseillabackend.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AdvertisementStatus {
    DRAFT(0),
    ACTIVE(1),
    ARCHIVED(2);

    private final int value;
    AdvertisementStatus(int value) {
        this.value = value;
    }
    @JsonValue
    public int getValue() {
        return value;
    }
}
