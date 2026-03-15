package com.energy.advisory_service.service;

import com.energy.advisory_service.dto.request.GenerateReportRequest;
import com.energy.advisory_service.dto.response.ReportResponse;

public interface ReportService {
    ReportResponse generateReport(GenerateReportRequest request);
    ReportResponse getReportByAssessmentId(String assessmentId);
}