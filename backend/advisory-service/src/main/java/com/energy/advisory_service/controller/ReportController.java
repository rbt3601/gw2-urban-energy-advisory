package com.energy.advisory_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energy.advisory_service.model.request.GenerateSustainabilityReportRequest;
import com.energy.advisory_service.model.response.GenerateSustainabilityReportResponse;
import com.energy.advisory_service.service.ReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping
@Tag(name = "Report API", description = "APIs for generating and retrieving sustainability advisory reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping({"/generateSustainabilityReport"})
    @Operation(summary = "Generate a sustainability report for a completed or partial assessment")
    public ResponseEntity<GenerateSustainabilityReportResponse> generateReport(@Valid @RequestBody GenerateSustainabilityReportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.generateReport(request));
    }

    @GetMapping("/reports/assessment/{assessmentId}")
    @Operation(summary = "Get a generated report by assessmentId")
    public ResponseEntity<GenerateSustainabilityReportResponse> getReportByAssessmentId(@PathVariable String assessmentId) {
        return ResponseEntity.ok(reportService.getReportByAssessmentId(assessmentId));
    }
}
