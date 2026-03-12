package com.energy.advisory_service.service;

import com.energy.advisory_service.adapter.CarbonServiceAdapter;
import com.energy.advisory_service.adapter.EnergyServiceAdapter;
import com.energy.advisory_service.adapter.WeatherServiceAdapter;
import com.energy.advisory_service.model.request.AssessmentRequest;
import com.energy.advisory_service.model.response.AdvisoryReportResponse;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReportService {

    private final EnergyServiceAdapter energyServiceAdapter;
    private final WeatherServiceAdapter weatherServiceAdapter;
    private final CarbonServiceAdapter carbonServiceAdapter;

    private final Map<String, AdvisoryReportResponse> reportStoreByAssessmentId = new ConcurrentHashMap<>();

    public ReportService(EnergyServiceAdapter energyServiceAdapter,
                         WeatherServiceAdapter weatherServiceAdapter,
                         CarbonServiceAdapter carbonServiceAdapter) {
        this.energyServiceAdapter = energyServiceAdapter;
        this.weatherServiceAdapter = weatherServiceAdapter;
        this.carbonServiceAdapter = carbonServiceAdapter;
    }

    public AdvisoryReportResponse generateReport(AssessmentRequest request) {
        double totalKWh = energyServiceAdapter.getTotalEnergyUsage(request);
        double peakKWh = energyServiceAdapter.getPeakEnergyUsage(request);
        String weatherSummary = weatherServiceAdapter.getWeatherSummary(request);
        double estimatedKgCO2 = carbonServiceAdapter.calculateCarbonEmission(totalKWh);

        AdvisoryReportResponse response = new AdvisoryReportResponse();
        response.setReportId("RPT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        response.setGeneratedAt(OffsetDateTime.now());
        response.setReportVersion("v1.0");

        AdvisoryReportResponse.SummaryMetrics summaryMetrics = new AdvisoryReportResponse.SummaryMetrics();
        summaryMetrics.setTotalKWh(totalKWh);
        summaryMetrics.setPeakKWh(peakKWh);
        summaryMetrics.setPeakPeriodStart(OffsetDateTime.now().minusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0));
        summaryMetrics.setPeakPeriodEnd(OffsetDateTime.now().minusDays(1).withHour(19).withMinute(0).withSecond(0).withNano(0));
        summaryMetrics.setEstimatedKgCO2(estimatedKgCO2);
        response.setSummaryMetrics(summaryMetrics);

        List<AdvisoryReportResponse.Recommendation> recommendations = new ArrayList<>();

        AdvisoryReportResponse.Recommendation rec1 = new AdvisoryReportResponse.Recommendation();
        rec1.setRecommendationId("REC001");
        rec1.setCategory("Efficiency");
        rec1.setActionText("Improve insulation in roof and walls.");
        rec1.setExpectedImpact("Reduces overall heating demand.");
        rec1.setPriority("High");
        recommendations.add(rec1);

        AdvisoryReportResponse.Recommendation rec2 = new AdvisoryReportResponse.Recommendation();
        rec2.setRecommendationId("REC002");
        rec2.setCategory("Usage");
        rec2.setActionText("Shift heavy appliance usage away from peak evening periods.");
        rec2.setExpectedImpact("Lowers peak energy consumption.");
        rec2.setPriority("Medium");
        recommendations.add(rec2);

        response.setRecommendations(recommendations);

        List<AdvisoryReportResponse.ContextNote> contextNotes = new ArrayList<>();
        AdvisoryReportResponse.ContextNote contextNote = new AdvisoryReportResponse.ContextNote();
        contextNote.setNoteType("Weather");
        contextNote.setMessage("Weather context: " + weatherSummary);
        contextNotes.add(contextNote);
        response.setContextNotes(contextNotes);

        List<AdvisoryReportResponse.DataCompletenessNote> dataNotes = new ArrayList<>();
        AdvisoryReportResponse.DataCompletenessNote dataNote = new AdvisoryReportResponse.DataCompletenessNote();
        dataNote.setMissingSource("ExternalMeterFeed");
        dataNote.setSeverity("Info");
        dataNote.setMessage("Values are simulated using adapter services.");
        dataNotes.add(dataNote);
        response.setDataCompletenessNotes(dataNotes);

        reportStoreByAssessmentId.put(request.getAssessmentId(), response);

        return response;
    }

    public AdvisoryReportResponse getReportByAssessmentId(String assessmentId) {
        AdvisoryReportResponse response = reportStoreByAssessmentId.get(assessmentId);

        if (response == null) {
            throw new RuntimeException("Report not found for assessment id: " + assessmentId);
        }

        return response;
    }
}