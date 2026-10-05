package com.houseilla.houseillabackend.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AdvertiserType {
    BROKER(0),
    BUILDER(1),
    INDIVIDUAL(2);

    private final int value;

    AdvertiserType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}
