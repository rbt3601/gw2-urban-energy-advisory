package com.energy.advisory_service.model.request;

import com.energy.advisory_service.enums.AssessmentStatus;

public class AssessmentRequest {

    private String assessmentId;
    private AssessmentStatus assessmentStatus;
    private String completedAt;

    public AssessmentRequest() {
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

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }
}