package com.energy.advisory_service.controller;

import com.energy.advisory_service.dto.request.GenerateReportRequest;
import com.energy.advisory_service.dto.response.ReportResponse;
import com.energy.advisory_service.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ReportResponse generateReport(@Valid @RequestBody GenerateReportRequest request) {
        return reportService.generateReport(request);
    }

    @GetMapping("/assessment/{assessmentId}")
    public ReportResponse getReportByAssessmentId(@PathVariable String assessmentId) {
        return reportService.getReportByAssessmentId(assessmentId);
    }
}