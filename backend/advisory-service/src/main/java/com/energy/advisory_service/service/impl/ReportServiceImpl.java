package com.energy.advisory_service.service.impl;

import com.energy.advisory_service.dto.request.GenerateReportRequest;
import com.energy.advisory_service.dto.response.*;
import com.energy.advisory_service.entity.Assessment;
import com.energy.advisory_service.entity.Report;
import com.energy.advisory_service.enums.AssessmentStatus;
import com.energy.advisory_service.repository.AssessmentRepository;
import com.energy.advisory_service.repository.ReportRepository;
import com.energy.advisory_service.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final AssessmentRepository assessmentRepository;

    public ReportServiceImpl(ReportRepository reportRepository,
                             AssessmentRepository assessmentRepository) {
        this.reportRepository = reportRepository;
        this.assessmentRepository = assessmentRepository;
    }

    @Override
    public ReportResponse generateReport(GenerateReportRequest request) {
        Assessment assessment = assessmentRepository.findByAssessmentId(request.getAssessmentId())
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        AssessmentStatus actualStatus = assessment.getAssessmentStatus();

        if (actualStatus == AssessmentStatus.PENDING) {
            throw new RuntimeException("Cannot generate report for a pending assessment");
        }

        Report report = new Report();
        report.setReportId(UUID.randomUUID().toString());
        report.setAssessmentId(assessment.getAssessmentId());
        report.setGeneratedAt(request.getCompletedAt().toLocalDateTime());
        report.setReportVersion("v1.0");
        report.setSummaryMetrics("Structured in API response");
        report.setRecommendations("Structured in API response");
        report.setContextNotes("Structured in API response");

        if (actualStatus == AssessmentStatus.PARTIAL) {
            report.setDataCompletenessNotes("Carbon data missing");
        } else {
            report.setDataCompletenessNotes(null);
        }

        Report saved = reportRepository.save(report);
        return mapToResponse(saved, actualStatus);
    }

    @Override
    public ReportResponse getReportByAssessmentId(String assessmentId) {
        Report saved = reportRepository.findByAssessmentId(assessmentId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        Assessment assessment = assessmentRepository.findByAssessmentId(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        return mapToResponse(saved, assessment.getAssessmentStatus());
    }

    private ReportResponse mapToResponse(Report saved, AssessmentStatus assessmentStatus) {
        ReportResponse response = new ReportResponse();
        response.setReportId(saved.getReportId());
        response.setGeneratedAt(saved.getGeneratedAt().atOffset(ZoneOffset.UTC));
        response.setReportVersion(saved.getReportVersion());

        ReportSummaryMetricsResponse metrics = new ReportSummaryMetricsResponse();
        metrics.setTotalKWh(128.4);
        metrics.setPeakKWh(11.8);
        metrics.setPeakPeriodStart(OffsetDateTime.parse("2026-01-15T18:00:00Z"));
        metrics.setPeakPeriodEnd(OffsetDateTime.parse("2026-01-15T19:00:00Z"));
        metrics.setEstimatedKgCO2(24.6);
        response.setSummaryMetrics(metrics);

        RecommendationResponse recommendation1 = new RecommendationResponse();
        recommendation1.setRecommendationId("REC-001");
        recommendation1.setCategory("behavioural");
        recommendation1.setActionText("Shift heavy appliance use away from peak evening hours.");
        recommendation1.setExpectedImpact("Lower peak demand and reduced estimated emissions.");
        recommendation1.setPriority("High");

        RecommendationResponse recommendation2 = new RecommendationResponse();
        recommendation2.setRecommendationId("REC-002");
        recommendation2.setCategory("efficiency");
        recommendation2.setActionText("Improve insulation and reduce standby power usage.");
        recommendation2.setExpectedImpact("Reduced total kWh consumption over the period.");
        recommendation2.setPriority("Medium");

        response.setRecommendations(List.of(recommendation1, recommendation2));

        ContextNoteResponse contextNote = new ContextNoteResponse();
        contextNote.setNoteType("weather");
        contextNote.setMessage("Colder weather likely increased heating demand during the selected period.");
        response.setContextNotes(List.of(contextNote));

        if (assessmentStatus == AssessmentStatus.PARTIAL) {
            DataCompletenessNoteResponse note = new DataCompletenessNoteResponse();
            note.setMissingSource("Carbon");
            note.setSeverity("Warning");
            note.setMessage("Carbon intensity data was unavailable, so emissions are estimated using partial context.");
            response.setDataCompletenessNotes(List.of(note));
        }

        return response;
    }
}