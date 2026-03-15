package com.energy.advisory_service.model.request;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

import com.energy.advisory_service.enums.AssessmentStatus;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GenerateSustainabilityReportRequest {

    @NotBlank(message = "assessmentId is required")
    private String assessmentId;

    @NotNull(message = "assessmentStatus is required")
    private AssessmentStatus assessmentStatus;

    @NotBlank(message = "completedAt is required")
    private String completedAt;

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public AssessmentStatus getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(AssessmentStatus assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    @AssertTrue(message = "completedAt must be a valid ISO 8601 date-time value")
    public boolean getCompletedAtValid() {
        if (completedAt == null || completedAt.isBlank()) {
            return true;
        }
        try {
            OffsetDateTime.parse(completedAt);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }
}