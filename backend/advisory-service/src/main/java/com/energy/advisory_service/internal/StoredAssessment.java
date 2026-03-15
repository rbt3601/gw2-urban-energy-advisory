package com.energy.advisory_service.internal;

import com.energy.advisory_service.enums.AssessmentStatus;
import com.energy.advisory_service.model.request.SubmitEnergyAssessmentRequest;
import com.energy.advisory_service.model.response.SubmitEnergyAssessmentResponse;

import java.time.OffsetDateTime;

public class StoredAssessment {
    private String assessmentId;
    private SubmitEnergyAssessmentRequest request;
    private OffsetDateTime startedAt;
    private OffsetDateTime completedAt;
    private AssessmentStatus assessmentStatus;
    private SubmitEnergyAssessmentResponse.DatasetWindow energyConsumptionData;
    private SubmitEnergyAssessmentResponse.DatasetWindow weatherContextData;
    private SubmitEnergyAssessmentResponse.CarbonDatasetWindow carbonIntensityData;
    private String errorMessage;
    private double totalKWh;
    private double peakKWh;
    private OffsetDateTime peakPeriodStart;
    private OffsetDateTime peakPeriodEnd;
    private double estimatedKgCO2;
    private String weatherSummary;
    private boolean simulatedEnergyData;
    private boolean usedFallbackCarbonFactor;

    public String getAssessmentId() { return assessmentId; }
    public void setAssessmentId(String assessmentId) { this.assessmentId = assessmentId; }
    public SubmitEnergyAssessmentRequest getRequest() { return request; }
    public void setRequest(SubmitEnergyAssessmentRequest request) { this.request = request; }
    public OffsetDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(OffsetDateTime startedAt) { this.startedAt = startedAt; }
    public OffsetDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(OffsetDateTime completedAt) { this.completedAt = completedAt; }
    public AssessmentStatus getAssessmentStatus() { return assessmentStatus; }
    public void setAssessmentStatus(AssessmentStatus assessmentStatus) { this.assessmentStatus = assessmentStatus; }
    public SubmitEnergyAssessmentResponse.DatasetWindow getEnergyConsumptionData() { return energyConsumptionData; }
    public void setEnergyConsumptionData(SubmitEnergyAssessmentResponse.DatasetWindow energyConsumptionData) { this.energyConsumptionData = energyConsumptionData; }
    public SubmitEnergyAssessmentResponse.DatasetWindow getWeatherContextData() { return weatherContextData; }
    public void setWeatherContextData(SubmitEnergyAssessmentResponse.DatasetWindow weatherContextData) { this.weatherContextData = weatherContextData; }
    public SubmitEnergyAssessmentResponse.CarbonDatasetWindow getCarbonIntensityData() { return carbonIntensityData; }
    public void setCarbonIntensityData(SubmitEnergyAssessmentResponse.CarbonDatasetWindow carbonIntensityData) { this.carbonIntensityData = carbonIntensityData; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public double getTotalKWh() { return totalKWh; }
    public void setTotalKWh(double totalKWh) { this.totalKWh = totalKWh; }
    public double getPeakKWh() { return peakKWh; }
    public void setPeakKWh(double peakKWh) { this.peakKWh = peakKWh; }
    public OffsetDateTime getPeakPeriodStart() { return peakPeriodStart; }
    public void setPeakPeriodStart(OffsetDateTime peakPeriodStart) { this.peakPeriodStart = peakPeriodStart; }
    public OffsetDateTime getPeakPeriodEnd() { return peakPeriodEnd; }
    public void setPeakPeriodEnd(OffsetDateTime peakPeriodEnd) { this.peakPeriodEnd = peakPeriodEnd; }
    public double getEstimatedKgCO2() { return estimatedKgCO2; }
    public void setEstimatedKgCO2(double estimatedKgCO2) { this.estimatedKgCO2 = estimatedKgCO2; }
    public String getWeatherSummary() { return weatherSummary; }
    public void setWeatherSummary(String weatherSummary) { this.weatherSummary = weatherSummary; }
    public boolean isSimulatedEnergyData() { return simulatedEnergyData; }
    public void setSimulatedEnergyData(boolean simulatedEnergyData) { this.simulatedEnergyData = simulatedEnergyData; }
    public boolean isUsedFallbackCarbonFactor() { return usedFallbackCarbonFactor; }
    public void setUsedFallbackCarbonFactor(boolean usedFallbackCarbonFactor) { this.usedFallbackCarbonFactor = usedFallbackCarbonFactor; }
}
