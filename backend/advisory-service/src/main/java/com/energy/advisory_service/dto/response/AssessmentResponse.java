package com.energy.advisory_service.dto.response;

import com.energy.advisory_service.enums.AssessmentStatus;

import java.time.OffsetDateTime;

public class AssessmentResponse {

    private String assessmentId;
    private OffsetDateTime startedAt;
    private OffsetDateTime completedAt;
    private AssessmentStatus assessmentStatus;
    private EnergyConsumptionDataResponse energyConsumptionData;
    private WeatherContextDataResponse weatherContextData;
    private CarbonIntensityDataResponse carbonIntensityData;
    private String errorMessage;

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public OffsetDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(OffsetDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public OffsetDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(OffsetDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public AssessmentStatus getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(AssessmentStatus assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public EnergyConsumptionDataResponse getEnergyConsumptionData() {
        return energyConsumptionData;
    }

    public void setEnergyConsumptionData(EnergyConsumptionDataResponse energyConsumptionData) {
        this.energyConsumptionData = energyConsumptionData;
    }

    public WeatherContextDataResponse getWeatherContextData() {
        return weatherContextData;
    }

    public void setWeatherContextData(WeatherContextDataResponse weatherContextData) {
        this.weatherContextData = weatherContextData;
    }

    public CarbonIntensityDataResponse getCarbonIntensityData() {
        return carbonIntensityData;
    }

    public void setCarbonIntensityData(CarbonIntensityDataResponse carbonIntensityData) {
        this.carbonIntensityData = carbonIntensityData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}