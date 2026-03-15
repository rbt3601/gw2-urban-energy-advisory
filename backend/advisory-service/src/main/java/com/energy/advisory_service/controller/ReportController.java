package com.energy.advisory_service.controller;

import com.energy.advisory_service.model.request.AssessmentRequest;
import com.energy.advisory_service.model.response.AdvisoryReportResponse;
import com.energy.advisory_service.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Tag(name = "Report Controller", description = "APIs for sustainability report generation")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/reports")
    @Operation(summary = "Generate sustainability report")
    public ResponseEntity<AdvisoryReportResponse> generateReport(@RequestBody AssessmentRequest request) {
        AdvisoryReportResponse response = reportService.generateReport(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/reports/assessment/{assessmentId}")
    @Operation(summary = "Get report by assessmentId")
    public ResponseEntity<AdvisoryReportResponse> getReportByAssessmentId(@PathVariable String assessmentId) {
        AdvisoryReportResponse response = reportService.getReportByAssessmentId(assessmentId);
        return ResponseEntity.ok(response);
    }
}