package com.energy.advisory_service.service;

import com.energy.advisory_service.entity.Assessment;
import com.energy.advisory_service.entity.Report;
import com.energy.advisory_service.enums.AssessmentStatus;
import com.energy.advisory_service.model.request.GenerateReportRequest;
import com.energy.advisory_service.model.response.AdvisoryReportResponse;
import com.energy.advisory_service.repository.AssessmentRepository;
import com.energy.advisory_service.repository.ReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final AssessmentRepository assessmentRepository;

    public ReportService(ReportRepository reportRepository,
                         AssessmentRepository assessmentRepository) {
        this.reportRepository = reportRepository;
        this.assessmentRepository = assessmentRepository;
    }

    public AdvisoryReportResponse generateReport(GenerateReportRequest request) {
        Assessment assessment = assessmentRepository.findByAssessmentId(request.getAssessmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment not found"));

        AssessmentStatus actualStatus = assessment.getAssessmentStatus();

        if (actualStatus == AssessmentStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot generate report for a pending assessment");
        }

        if (request.getAssessmentStatus() != actualStatus) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "assessmentStatus does not match the stored assessment status");
        }

        if (assessment.getCompletedAt() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Assessment completion time is not available");
        }

        OffsetDateTime actualCompletedAt = assessment.getCompletedAt()
                .atOffset(ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.SECONDS);

        OffsetDateTime requestCompletedAt = request.getCompletedAt()
                .withOffsetSameInstant(ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.SECONDS);

        if (!requestCompletedAt.toInstant().equals(actualCompletedAt.toInstant())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "completedAt does not match the stored assessment completion time");
        }

        Report report = new Report();

        // You can keep UUID if you want:
        // report.setReportId(UUID.randomUUID().toString());

        // Or use a cleaner readable ID:
        report.setReportId("rep-" + UUID.randomUUID().toString().substring(0, 8));

        report.setAssessmentId(assessment.getAssessmentId());
        report.setGeneratedAt(LocalDateTime.now(ZoneOffset.UTC));
        report.setReportVersion("v1.0");
        report.setSummaryMetrics("Structured in API response");
        report.setRecommendations("Structured in API response");
        report.setContextNotes("Structured in API response");

        if (actualStatus == AssessmentStatus.PARTIAL) {
            report.setDataCompletenessNotes("Some contextual sources are missing");
        } else if (actualStatus == AssessmentStatus.FAILED) {
            report.setDataCompletenessNotes("Assessment failed, report contains failure information only");
        } else {
            report.setDataCompletenessNotes(null);
        }

        Report saved = reportRepository.save(report);
        return mapToResponse(saved, assessment);
    }

    public AdvisoryReportResponse getReportByReportId(String reportId) {
        Report saved = reportRepository.findByReportId(reportId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found"));

        Assessment assessment = assessmentRepository.findByAssessmentId(saved.getAssessmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment not found"));

        return mapToResponse(saved, assessment);
    }

    private AdvisoryReportResponse mapToResponse(Report saved, Assessment assessment) {
        AssessmentStatus assessmentStatus = assessment.getAssessmentStatus();

        AdvisoryReportResponse response = new AdvisoryReportResponse();
        response.setReportId(saved.getReportId());
        response.setGeneratedAt(saved.getGeneratedAt().atOffset(ZoneOffset.UTC));
        response.setReportVersion(saved.getReportVersion());

        if (assessmentStatus == AssessmentStatus.FAILED) {
            AdvisoryReportResponse.DataCompletenessNote note = new AdvisoryReportResponse.DataCompletenessNote();
            note.setMissingSource("Energy");
            note.setSeverity("Critical");
            note.setMessage(
                    assessment.getErrorMessage() != null
                            ? assessment.getErrorMessage()
                            : "Energy consumption data was unavailable, so no analytical report could be produced."
            );

            response.setSummaryMetrics(null);
            response.setRecommendations(List.of());
            response.setContextNotes(List.of());
            response.setDataCompletenessNotes(List.of(note));
            return response;
        }

        AdvisoryReportResponse.SummaryMetrics metrics = new AdvisoryReportResponse.SummaryMetrics();
        metrics.setTotalKWh(128.4);
        metrics.setPeakKWh(11.8);
        metrics.setPeakPeriodStart(OffsetDateTime.parse("2026-01-15T18:00:00Z"));
        metrics.setPeakPeriodEnd(OffsetDateTime.parse("2026-01-15T19:00:00Z"));
        metrics.setEstimatedKgCO2(24.6);
        response.setSummaryMetrics(metrics);

        AdvisoryReportResponse.Recommendation rec1 = new AdvisoryReportResponse.Recommendation();
        rec1.setRecommendationId("REC-001");
        rec1.setCategory("behavioural");
        rec1.setActionText("Shift heavy appliance use away from peak evening hours.");
        rec1.setExpectedImpact("Lower peak demand and reduced estimated emissions.");
        rec1.setPriority("High");

        AdvisoryReportResponse.Recommendation rec2 = new AdvisoryReportResponse.Recommendation();
        rec2.setRecommendationId("REC-002");
        rec2.setCategory("efficiency");
        rec2.setActionText("Improve insulation and reduce standby power usage.");
        rec2.setExpectedImpact("Reduced total kWh consumption over the period.");
        rec2.setPriority("Medium");

        response.setRecommendations(List.of(rec1, rec2));

        AdvisoryReportResponse.ContextNote contextNote = new AdvisoryReportResponse.ContextNote();
        contextNote.setNoteType("weather");
        contextNote.setMessage("Colder weather likely increased heating demand during the selected period.");
        response.setContextNotes(List.of(contextNote));

        if (assessmentStatus == AssessmentStatus.PARTIAL) {
            AdvisoryReportResponse.DataCompletenessNote note = new AdvisoryReportResponse.DataCompletenessNote();
            note.setMissingSource("Carbon/Weather");
            note.setSeverity("Warning");
            note.setMessage("Some contextual data was unavailable, so this report is partial.");
            response.setDataCompletenessNotes(List.of(note));
        } else {
            response.setDataCompletenessNotes(null);
        }

        return response;
    }
}