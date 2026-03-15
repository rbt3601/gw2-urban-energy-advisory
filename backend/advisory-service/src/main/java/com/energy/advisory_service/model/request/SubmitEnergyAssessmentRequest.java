package com.energy.advisory_service.model.request;

import com.energy.advisory_service.model.common.Property;
import com.energy.advisory_service.model.common.TimeWindow;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

public class SubmitEnergyAssessmentRequest {

    @NotBlank(message = "requestId is required")
    private String requestId;

    @NotBlank(message = "createdAt is required")
    private String createdAt;

    @Valid
    @NotNull(message = "property is required")
    private Property property;

    @Valid
    @NotNull(message = "timeWindow is required")
    private TimeWindow timeWindow;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public TimeWindow getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(TimeWindow timeWindow) {
        this.timeWindow = timeWindow;
    }

    @AssertTrue(message = "createdAt must be a valid ISO 8601 date-time value")
    public boolean getCreatedAtValid() {
        if (createdAt == null || createdAt.isBlank()) {
            return true;
        }
        try {
            OffsetDateTime.parse(createdAt);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }
}