package com.energy.advisory_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Granularity {
    HOURLY("hourly"),
    DAILY("daily");

    private final String value;

    Granularity(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Granularity fromValue(String value) {
        for (Granularity granularity : values()) {
            if (granularity.value.equalsIgnoreCase(value)) {
                return granularity;
            }
        }
        throw new IllegalArgumentException("Invalid granularity. Allowed values: hourly, daily");
    }
}