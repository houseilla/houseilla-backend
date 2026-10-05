package com.houseilla.houseillabackend.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AdvertisementPropertyType {
    PRIVATE_LAND(0),
    HOUSE(1),
    FLAT(2),
    COMMERCIAL_LAND(3),
    SHOP(4),
    OFFICE(5),
    WAREHOUSE(6);

    private final int value;

    AdvertisementPropertyType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public static String getKeyFromValue(int value) {
        for (AdvertisementPropertyType type : AdvertisementPropertyType.values()) {
            if (type.getValue() == value) {
                return type.name(); // Returns standard string names like "HOUSE" or "PRIVATE_LAND"
            }
        }
        return "UNKNOWN";
    }
}
