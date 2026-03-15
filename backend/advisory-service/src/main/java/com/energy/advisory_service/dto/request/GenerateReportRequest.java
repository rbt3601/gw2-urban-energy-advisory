package com.energy.advisory_service.dto.request;

import com.energy.advisory_service.enums.AssessmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class GenerateReportRequest {

    @NotBlank
    private String assessmentId;

    @NotNull
    private AssessmentStatus assessmentStatus;

    @NotNull
    private OffsetDateTime completedAt;

    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "assessmentStatus must be Completed, Partial, or Failed for report generation")
    public boolean isValidAssessmentStatusForReport() {
        return assessmentStatus == null || assessmentStatus != AssessmentStatus.PENDING;
    }

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

    public OffsetDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(OffsetDateTime completedAt) {
        this.completedAt = completedAt;
    }
}