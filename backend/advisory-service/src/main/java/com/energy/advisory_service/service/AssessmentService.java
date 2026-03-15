package com.energy.advisory_service.service;

import com.energy.advisory_service.dto.request.SubmitEnergyAssessmentRequest;
import com.energy.advisory_service.dto.response.AssessmentResponse;

public interface AssessmentService {
    AssessmentResponse submitAssessment(SubmitEnergyAssessmentRequest request);
    AssessmentResponse getAssessmentByAssessmentId(String assessmentId);
}