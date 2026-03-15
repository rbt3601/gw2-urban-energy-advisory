package com.energy.advisory_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum PropertyType {
    HOME("home"),
    SMALL_BUILDING("smallBuilding");

    private final String jsonValue;

    PropertyType(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    @JsonValue
    public String getJsonValue() {
        return jsonValue;
    }

    @JsonCreator
    public static PropertyType fromValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.jsonValue.equalsIgnoreCase(value) || v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported propertyType: " + value));
    }
}
