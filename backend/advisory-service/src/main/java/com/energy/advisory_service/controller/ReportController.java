package com.energy.advisory_service.controller;

import com.energy.advisory_service.model.request.GenerateReportRequest;
import com.energy.advisory_service.model.response.AdvisoryReportResponse;
import com.energy.advisory_service.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@Tag(name = "Report Controller", description = "APIs for sustainability report generation")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    @Operation(summary = "Generate sustainability report")
    public ResponseEntity<AdvisoryReportResponse> generateReport(@Valid @RequestBody GenerateReportRequest request) {
        AdvisoryReportResponse response = reportService.generateReport(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{reportId}")
    @Operation(summary = "Get report by reportId")
    public ResponseEntity<AdvisoryReportResponse> getReportByReportId(@PathVariable String reportId) {
        return ResponseEntity.ok(reportService.getReportByReportId(reportId));
    }
}