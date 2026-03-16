package com.energy.advisory_service.model.request;

import com.energy.advisory_service.enums.Granularity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class TimeWindowRequest {

    @NotNull(message = "timeWindow.startDateTime is required")
    private OffsetDateTime startDateTime;

    @NotNull(message = "timeWindow.endDateTime is required")
    private OffsetDateTime endDateTime;

    @NotNull(message = "timeWindow.granularity is required")
    private Granularity granularity;

    public OffsetDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(OffsetDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public OffsetDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(OffsetDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Granularity getGranularity() {
        return granularity;
    }

    public void setGranularity(Granularity granularity) {
        this.granularity = granularity;
    }

    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "timeWindow.endDateTime must be after timeWindow.startDateTime")
    public boolean isValidTimeWindow() {
        if (startDateTime == null || endDateTime == null) {
            return true;
        }
        return endDateTime.isAfter(startDateTime);
    }
}