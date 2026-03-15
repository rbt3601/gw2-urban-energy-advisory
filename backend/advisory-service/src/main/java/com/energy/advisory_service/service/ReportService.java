package com.energy.advisory_service.service;

import com.energy.advisory_service.enums.AssessmentStatus;
import com.energy.advisory_service.exception.ApiException;
import com.energy.advisory_service.internal.StoredAssessment;
import com.energy.advisory_service.model.request.GenerateSustainabilityReportRequest;
import com.energy.advisory_service.model.response.GenerateSustainabilityReportResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReportService {

    private final AssessmentService assessmentService;
    private final Map<String, GenerateSustainabilityReportResponse> reportStoreByAssessmentId = new ConcurrentHashMap<>();

    public ReportService(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    public GenerateSustainabilityReportResponse generateReport(GenerateSustainabilityReportRequest request) {
        StoredAssessment stored = assessmentService.getStoredAssessment(request.getAssessmentId());
        validateReportRequestAgainstStoredAssessment(request, stored);

        if (stored.getAssessmentStatus() == AssessmentStatus.FAILED) {
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "A sustainability report cannot be generated for a failed assessment");
        }

        GenerateSustainabilityReportResponse response = new GenerateSustainabilityReportResponse();
        response.setReportId("RPT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        response.setGeneratedAt(OffsetDateTime.now());
        response.setReportVersion("v1.0");

        GenerateSustainabilityReportResponse.SummaryMetrics summaryMetrics = new GenerateSustainabilityReportResponse.SummaryMetrics();
        summaryMetrics.setTotalKWh(stored.getTotalKWh());
        summaryMetrics.setPeakKWh(stored.getPeakKWh());
        summaryMetrics.setPeakPeriodStart(stored.getPeakPeriodStart());
        summaryMetrics.setPeakPeriodEnd(stored.getPeakPeriodEnd());
        summaryMetrics.setEstimatedKgCO2(stored.getEstimatedKgCO2());
        response.setSummaryMetrics(summaryMetrics);

        response.setRecommendations(buildRecommendations(stored));
        response.setContextNotes(buildContextNotes(stored));
        response.setDataCompletenessNotes(buildCompletenessNotes(stored));

        reportStoreByAssessmentId.put(request.getAssessmentId(), response);
        return response;
    }

    public GenerateSustainabilityReportResponse getReportByAssessmentId(String assessmentId) {
        GenerateSustainabilityReportResponse response = reportStoreByAssessmentId.get(assessmentId);
        if (response == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Report not found for assessment id: " + assessmentId);
        }
        return response;
    }

    private void validateReportRequestAgainstStoredAssessment(GenerateSustainabilityReportRequest request, StoredAssessment stored) {
        if (stored.getCompletedAt() == null) {
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Assessment has not completed and cannot be used to generate a report");
        }
        if (!stored.getAssessmentStatus().equals(request.getAssessmentStatus())) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "assessmentStatus in request does not match stored assessment status");
        }
        if (!stored.getCompletedAt().toString().equals(request.getCompletedAt())) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "completedAt in request does not match stored assessment completion time");
        }
    }

    private List<GenerateSustainabilityReportResponse.Recommendation> buildRecommendations(StoredAssessment stored) {
        List<GenerateSustainabilityReportResponse.Recommendation> recommendations = new ArrayList<>();

        GenerateSustainabilityReportResponse.Recommendation peak = new GenerateSustainabilityReportResponse.Recommendation();
        peak.setRecommendationId("REC001");
        peak.setCategory("scheduling");
        peak.setActionText("Shift heavy appliance usage away from the peak window shown in the report.");
        peak.setExpectedImpact("Can lower peak demand and reduce strain on the grid.");
        peak.setPriority("High");
        recommendations.add(peak);

        GenerateSustainabilityReportResponse.Recommendation efficiency = new GenerateSustainabilityReportResponse.Recommendation();
        efficiency.setRecommendationId("REC002");
        efficiency.setCategory("efficiency");
        efficiency.setActionText(stored.getRequest().getProperty().getPropertyType().getJsonValue().equals("smallBuilding")
                ? "Review insulation and building controls to reduce persistent heating losses."
                : "Improve insulation and heating efficiency to reduce baseline demand.");
        efficiency.setExpectedImpact("Reduces total consumption over the selected period.");
        efficiency.setPriority("Medium");
        recommendations.add(efficiency);

        return recommendations;
    }

    private List<GenerateSustainabilityReportResponse.ContextNote> buildContextNotes(StoredAssessment stored) {
        List<GenerateSustainabilityReportResponse.ContextNote> notes = new ArrayList<>();
        if (stored.getWeatherSummary() != null) {
            GenerateSustainabilityReportResponse.ContextNote weather = new GenerateSustainabilityReportResponse.ContextNote();
            weather.setNoteType("weather");
            weather.setMessage(stored.getWeatherSummary());
            notes.add(weather);
        }
        return notes;
    }

    private List<GenerateSustainabilityReportResponse.DataCompletenessNote> buildCompletenessNotes(StoredAssessment stored) {
        List<GenerateSustainabilityReportResponse.DataCompletenessNote> notes = new ArrayList<>();

        if (stored.isSimulatedEnergyData()) {
            GenerateSustainabilityReportResponse.DataCompletenessNote note = new GenerateSustainabilityReportResponse.DataCompletenessNote();
            note.setMissingSource("Energy");
            note.setSeverity("Info");
            note.setMessage("Energy values are simulated by the adapter because no live smart meter integration is configured.");
            notes.add(note);
        }

        if (stored.getWeatherContextData() == null) {
            GenerateSustainabilityReportResponse.DataCompletenessNote note = new GenerateSustainabilityReportResponse.DataCompletenessNote();
            note.setMissingSource("Weather");
            note.setSeverity("Warning");
            note.setMessage("Weather context was unavailable, so behavioural interpretation is less specific.");
            notes.add(note);
        }

        if (stored.getCarbonIntensityData() == null) {
            GenerateSustainabilityReportResponse.DataCompletenessNote note = new GenerateSustainabilityReportResponse.DataCompletenessNote();
            note.setMissingSource("Carbon");
            note.setSeverity("Warning");
            note.setMessage("Carbon intensity data was unavailable, so emissions were estimated using a fallback regional factor.");
            notes.add(note);
        }

        return notes;
    }
}
