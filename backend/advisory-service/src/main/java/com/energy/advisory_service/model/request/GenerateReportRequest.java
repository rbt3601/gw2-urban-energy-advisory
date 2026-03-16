package com.energy.advisory_service.model.request;

import com.energy.advisory_service.enums.AssessmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class GenerateReportRequest {

    @NotBlank(message = "assessmentId is required")
    private String assessmentId;

    @NotNull(message = "assessmentStatus is required")
    @Schema(example = "Completed", allowableValues = {"Completed", "Partial", "Failed"})
    private AssessmentStatus assessmentStatus;

    @NotNull(message = "completedAt is required")
    private OffsetDateTime completedAt;

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

    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "assessmentStatus must be Completed, Partial, or Failed for report generation")
    public boolean isValidAssessmentStatusForReport() {
        return assessmentStatus == null
                || assessmentStatus == AssessmentStatus.COMPLETED
                || assessmentStatus == AssessmentStatus.PARTIAL
                || assessmentStatus == AssessmentStatus.FAILED;
    }
}