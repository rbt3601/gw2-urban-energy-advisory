package com.energy.advisory_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AssessmentStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    PARTIAL("Partial"),
    FAILED("Failed");

    private final String value;

    AssessmentStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static AssessmentStatus fromValue(String value) {
        for (AssessmentStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid assessmentStatus. Allowed values: Pending, Completed, Partial, Failed");
    }
}