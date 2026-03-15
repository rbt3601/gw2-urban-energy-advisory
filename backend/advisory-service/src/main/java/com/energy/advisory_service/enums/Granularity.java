package com.energy.advisory_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Granularity {
    HOURLY("hourly"),
    DAILY("daily");

    private final String jsonValue;

    Granularity(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    @JsonValue
    public String getJsonValue() {
        return jsonValue;
    }

    @JsonCreator
    public static Granularity fromValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.jsonValue.equalsIgnoreCase(value) || v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported granularity: " + value));
    }
}
