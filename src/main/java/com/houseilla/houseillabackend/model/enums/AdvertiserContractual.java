package com.houseilla.houseillabackend.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AdvertiserContractual {
    FREE(0),
    PREMIUM(1);

    private final int value;

    AdvertiserContractual(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}
