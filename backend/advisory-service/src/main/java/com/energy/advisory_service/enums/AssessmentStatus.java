package com.energy.advisory_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum AssessmentStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    PARTIAL("Partial"),
    FAILED("Failed");

    private final String jsonValue;

    AssessmentStatus(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    @JsonValue
    public String getJsonValue() {
        return jsonValue;
    }

    @JsonCreator
    public static AssessmentStatus fromValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.jsonValue.equalsIgnoreCase(value) || v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported assessmentStatus: " + value));
    }
}
