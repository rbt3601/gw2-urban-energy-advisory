package com.energy.advisory_service.model.response;

import com.energy.advisory_service.enums.AssessmentStatus;

public class AssessmentResponse {

    private String assessmentId;
    private String startedAt;
    private String completedAt;
    private AssessmentStatus assessmentStatus;
    private double totalEnergyUsage;
    private double peakEnergyUsage;
    private String weatherSummary;
    private double carbonEmission;

    public AssessmentResponse() {
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public AssessmentStatus getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(AssessmentStatus assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public double getTotalEnergyUsage() {
        return totalEnergyUsage;
    }

    public void setTotalEnergyUsage(double totalEnergyUsage) {
        this.totalEnergyUsage = totalEnergyUsage;
    }

    public double getPeakEnergyUsage() {
        return peakEnergyUsage;
    }

    public void setPeakEnergyUsage(double peakEnergyUsage) {
        this.peakEnergyUsage = peakEnergyUsage;
    }

    public String getWeatherSummary() {
        return weatherSummary;
    }

    public void setWeatherSummary(String weatherSummary) {
        this.weatherSummary = weatherSummary;
    }

    public double getCarbonEmission() {
        return carbonEmission;
    }

    public void setCarbonEmission(double carbonEmission) {
        this.carbonEmission = carbonEmission;
    }
}