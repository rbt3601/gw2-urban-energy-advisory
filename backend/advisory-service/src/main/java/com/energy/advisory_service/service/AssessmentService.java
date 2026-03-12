package com.energy.advisory_service.service;

import com.energy.advisory_service.adapter.CarbonServiceAdapter;
import com.energy.advisory_service.adapter.EnergyServiceAdapter;
import com.energy.advisory_service.adapter.WeatherServiceAdapter;
import com.energy.advisory_service.enums.AssessmentStatus;
import com.energy.advisory_service.model.request.SubmitAssessmentRequest;
import com.energy.advisory_service.model.response.AssessmentResponse;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AssessmentService {

    private final EnergyServiceAdapter energyServiceAdapter;
    private final WeatherServiceAdapter weatherServiceAdapter;
    private final CarbonServiceAdapter carbonServiceAdapter;

    private final Map<String, AssessmentResponse> assessmentStore = new ConcurrentHashMap<>();

    public AssessmentService(EnergyServiceAdapter energyServiceAdapter,
                             WeatherServiceAdapter weatherServiceAdapter,
                             CarbonServiceAdapter carbonServiceAdapter) {
        this.energyServiceAdapter = energyServiceAdapter;
        this.weatherServiceAdapter = weatherServiceAdapter;
        this.carbonServiceAdapter = carbonServiceAdapter;
    }

    public AssessmentResponse submitAssessment(SubmitAssessmentRequest request) {
        double totalEnergyUsage = energyServiceAdapter.getTotalEnergyUsage(request);
        double peakEnergyUsage = energyServiceAdapter.getPeakEnergyUsage(request);
        String weatherSummary = weatherServiceAdapter.getWeatherSummary(request);
        double carbonEmission = carbonServiceAdapter.calculateCarbonEmission(totalEnergyUsage);

        AssessmentResponse response = new AssessmentResponse();
        String assessmentId = "ASM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        response.setAssessmentId(assessmentId);
        response.setStartedAt(OffsetDateTime.now().minusSeconds(2).toString());
        response.setCompletedAt(OffsetDateTime.now().toString());
        response.setAssessmentStatus(AssessmentStatus.COMPLETED);
        response.setTotalEnergyUsage(totalEnergyUsage);
        response.setPeakEnergyUsage(peakEnergyUsage);
        response.setWeatherSummary(weatherSummary);
        response.setCarbonEmission(carbonEmission);

        assessmentStore.put(assessmentId, response);
        return response;
    }

    public AssessmentResponse getAssessmentById(String assessmentId) {
        AssessmentResponse response = assessmentStore.get(assessmentId);

        if (response == null) {
            throw new RuntimeException("Assessment not found with id: " + assessmentId);
        }

        return response;
    }
}