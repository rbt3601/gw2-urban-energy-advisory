package com.energy.advisory_service.model.common;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

import com.energy.advisory_service.enums.Granularity;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TimeWindow {

    @NotBlank(message = "startDateTime is required")
    private String startDateTime;

    @NotBlank(message = "endDateTime is required")
    private String endDateTime;

    @NotNull(message = "granularity is required")
    private Granularity granularity;

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Granularity getGranularity() {
        return granularity;
    }

    public void setGranularity(Granularity granularity) {
        this.granularity = granularity;
    }

    @AssertTrue(message = "startDateTime must be a valid ISO 8601 date-time value")
    public boolean getStartDateTimeValid() {
        if (startDateTime == null || startDateTime.isBlank()) {
            return true;
        }
        try {
            OffsetDateTime.parse(startDateTime);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    @AssertTrue(message = "endDateTime must be a valid ISO 8601 date-time value")
    public boolean getEndDateTimeValid() {
        if (endDateTime == null || endDateTime.isBlank()) {
            return true;
        }
        try {
            OffsetDateTime.parse(endDateTime);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    @AssertTrue(message = "endDateTime must be after startDateTime")
    public boolean getChronologicallyValid() {
        if (startDateTime == null || endDateTime == null || startDateTime.isBlank() || endDateTime.isBlank()) {
            return true;
        }
        try {
            OffsetDateTime start = OffsetDateTime.parse(startDateTime);
            OffsetDateTime end = OffsetDateTime.parse(endDateTime);
            return end.isAfter(start);
        } catch (DateTimeParseException ex) {
            return true;
        }
    }
}