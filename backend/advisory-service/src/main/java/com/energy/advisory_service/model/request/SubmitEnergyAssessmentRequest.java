package com.energy.advisory_service.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.OffsetDateTime;

public class SubmitEnergyAssessmentRequest {

    @NotBlank(message = "requestId is required")
    private String requestId;

    @NotNull(message = "createdAt is required")
    private OffsetDateTime createdAt;

    @Valid
    @NotNull(message = "property is required")
    private PropertyRequest property;

    @Valid
    @NotNull(message = "timeWindow is required")
    private TimeWindowRequest timeWindow;

    @Pattern(
            regexp = "^(energy_fail|weather_fail|carbon_fail)?$",
            message = "testMode must be one of: energy_fail, weather_fail, carbon_fail"
    )
    private String testMode;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PropertyRequest getProperty() {
        return property;
    }

    public void setProperty(PropertyRequest property) {
        this.property = property;
    }

    public TimeWindowRequest getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(TimeWindowRequest timeWindow) {
        this.timeWindow = timeWindow;
    }

    public String getTestMode() {
        return testMode;
    }

    public void setTestMode(String testMode) {
        this.testMode = testMode;
    }
}