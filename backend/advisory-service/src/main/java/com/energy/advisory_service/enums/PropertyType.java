package com.energy.advisory_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PropertyType {
    HOME("home"),
    SMALL_BUILDING("smallBuilding");

    private final String value;

    PropertyType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PropertyType fromValue(String value) {
        for (PropertyType propertyType : values()) {
            if (propertyType.value.equalsIgnoreCase(value)) {
                return propertyType;
            }
        }
        throw new IllegalArgumentException("Invalid property type: " + value);
    }
}