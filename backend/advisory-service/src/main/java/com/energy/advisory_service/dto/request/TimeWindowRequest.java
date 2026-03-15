package com.energy.advisory_service.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.energy.advisory_service.enums.Granularity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class TimeWindowRequest {

    @NotNull
    private OffsetDateTime startDateTime;

    @NotNull
    private OffsetDateTime endDateTime;

    @NotNull
    private Granularity granularity;

    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "endDateTime must be after startDateTime")
    public boolean isValidTimeWindow() {
        return startDateTime == null || endDateTime == null || endDateTime.isAfter(startDateTime);
    }

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
}