package com.energy.advisory_service.service;

import com.energy.advisory_service.adapter.CarbonServiceAdapter;
import com.energy.advisory_service.adapter.EnergyServiceAdapter;
import com.energy.advisory_service.adapter.WeatherServiceAdapter;
import com.energy.advisory_service.enums.AssessmentStatus;
import com.energy.advisory_service.exception.ApiException;
import com.energy.advisory_service.internal.StoredAssessment;
import com.energy.advisory_service.model.request.SubmitEnergyAssessmentRequest;
import com.energy.advisory_service.model.response.SubmitEnergyAssessmentResponse;
import org.springframework.http.HttpStatus;
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
    private final Map<String, StoredAssessment> assessmentStore = new ConcurrentHashMap<>();

    public AssessmentService(EnergyServiceAdapter energyServiceAdapter,
                             WeatherServiceAdapter weatherServiceAdapter,
                             CarbonServiceAdapter carbonServiceAdapter) {
        this.energyServiceAdapter = energyServiceAdapter;
        this.weatherServiceAdapter = weatherServiceAdapter;
        this.carbonServiceAdapter = carbonServiceAdapter;
    }

    public SubmitEnergyAssessmentResponse submitAssessment(SubmitEnergyAssessmentRequest request) {
        String assessmentId = "ASM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        OffsetDateTime startedAt = OffsetDateTime.now();

        StoredAssessment stored = new StoredAssessment();
        stored.setAssessmentId(assessmentId);
        stored.setRequest(request);
        stored.setStartedAt(startedAt);

        try {
            EnergyServiceAdapter.EnergyAnalysis energy = energyServiceAdapter.getEnergyAnalysis(request);
            stored.setTotalKWh(energy.getTotalKWh());
            stored.setPeakKWh(energy.getPeakKWh());
            stored.setPeakPeriodStart(energy.getPeakPeriodStart());
            stored.setPeakPeriodEnd(energy.getPeakPeriodEnd());
            stored.setSimulatedEnergyData(energy.isSimulated());
            stored.setEnergyConsumptionData(toDataset(energy.getSourceName(), energy.getDataFrom(), energy.getDataTo()));
        } catch (ApiException ex) {
            stored.setAssessmentStatus(AssessmentStatus.FAILED);
            stored.setErrorMessage(ex.getMessage());
            assessmentStore.put(assessmentId, stored);
            return toFailedResponse(stored);
        }

        WeatherServiceAdapter.WeatherResult weather = weatherServiceAdapter.getWeatherContext(request);
        if (weather != null) {
            stored.setWeatherContextData(toDataset(weather.getSourceName(), weather.getDataFrom(), weather.getDataTo()));
            stored.setWeatherSummary(weather.getSummary());
        }

        CarbonServiceAdapter.CarbonResult carbon = carbonServiceAdapter.getCarbonIntensityContext(request, stored.getTotalKWh());
        if (carbon != null) {
            SubmitEnergyAssessmentResponse.CarbonDatasetWindow carbonWindow = new SubmitEnergyAssessmentResponse.CarbonDatasetWindow();
            carbonWindow.setSourceName(carbon.getSourceName());
            carbonWindow.setRegion(carbon.getRegion());
            carbonWindow.setDataFrom(carbon.getDataFrom());
            carbonWindow.setDataTo(carbon.getDataTo());
            stored.setCarbonIntensityData(carbonWindow);
            stored.setEstimatedKgCO2(carbon.getEstimatedKgCO2());
        } else {
            stored.setUsedFallbackCarbonFactor(true);
            stored.setEstimatedKgCO2(carbonServiceAdapter.calculateFallbackCarbonEmission(stored.getTotalKWh()));
        }

        stored.setCompletedAt(OffsetDateTime.now());
        stored.setAssessmentStatus((weather != null && carbon != null) ? AssessmentStatus.COMPLETED : AssessmentStatus.PARTIAL);
        assessmentStore.put(assessmentId, stored);
        return toResponse(stored);
    }

    public SubmitEnergyAssessmentResponse getAssessmentById(String assessmentId) {
        return toResponse(getStoredAssessment(assessmentId));
    }

    public StoredAssessment getStoredAssessment(String assessmentId) {
        StoredAssessment storedAssessment = assessmentStore.get(assessmentId);
        if (storedAssessment == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Assessment not found with id: " + assessmentId);
        }
        return storedAssessment;
    }

    private SubmitEnergyAssessmentResponse toFailedResponse(StoredAssessment stored) {
        SubmitEnergyAssessmentResponse response = new SubmitEnergyAssessmentResponse();
        response.setAssessmentId(stored.getAssessmentId());
        response.setStartedAt(stored.getStartedAt().toString());
        response.setAssessmentStatus(AssessmentStatus.FAILED);
        response.setErrorMessage(stored.getErrorMessage());
        return response;
    }

    private SubmitEnergyAssessmentResponse toResponse(StoredAssessment stored) {
        if (stored.getAssessmentStatus() == AssessmentStatus.FAILED) {
            return toFailedResponse(stored);
        }

        SubmitEnergyAssessmentResponse response = new SubmitEnergyAssessmentResponse();
        response.setAssessmentId(stored.getAssessmentId());
        response.setStartedAt(stored.getStartedAt().toString());
        response.setCompletedAt(stored.getCompletedAt().toString());
        response.setAssessmentStatus(stored.getAssessmentStatus());
        response.setEnergyConsumptionData(stored.getEnergyConsumptionData());
        response.setWeatherContextData(stored.getWeatherContextData());
        response.setCarbonIntensityData(stored.getCarbonIntensityData());
        return response;
    }

    private SubmitEnergyAssessmentResponse.DatasetWindow toDataset(String sourceName, String dataFrom, String dataTo) {
        SubmitEnergyAssessmentResponse.DatasetWindow dataset = new SubmitEnergyAssessmentResponse.DatasetWindow();
        dataset.setSourceName(sourceName);
        dataset.setDataFrom(dataFrom);
        dataset.setDataTo(dataTo);
        return dataset;
    }
}
